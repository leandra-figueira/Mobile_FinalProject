package net.lfernandes.myapplication

import android.content.Intent
import net.lfernandes.myapplication.Dialogs.NoteTitleDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*
import net.lfernandes.myapplication.Dialogs.NoteCategoryDialog
import net.lfernandes.myapplication.Dialogs.NoteDescriptionDialog
import net.lfernandes.myapplication.Dialogs.NoteLocationDialog

class NewNoteActivity : AppCompatActivity() {

    var id: String = ""
    var title: String = ""
    var category: String = ""
    var location: String = ""
    var description: String = ""
    var categoryId: String = ""
    lateinit var btnSave: Button
    lateinit var btnCancel: Button

    // required to connect to firebase:
    lateinit var mDatabase: DatabaseReference
    var categoryList: MutableList<Category>? = null
    lateinit var cAdapter: CategoryAdapter
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        mDatabase = FirebaseDatabase.getInstance().reference
        categoryList = mutableListOf()
        cAdapter = CategoryAdapter(this, categoryList!!)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)
        btnSave = findViewById(R.id.btnSaveNote)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            saveData()
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            addDataToList(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.child("categories").children.iterator()
        while (items.hasNext()) {
            val currentItem = items.next()
            val category = Category.create()
            val map = currentItem.value as HashMap<String, Any>

            category.id = currentItem.key
            category.name = map["name"] as String?
            categoryList!!.add(category);
        }
        cAdapter.notifyDataSetChanged()
    }

    fun showNoteCategoryDialog(v: View){
        val dialog = NoteCategoryDialog()
        dialog.show(supportFragmentManager, "NoteCategoryDialog")
    }
    fun showNoteTitleDialog(v: View) {
        val dialog = NoteTitleDialog()
        dialog.show(supportFragmentManager, "NoteTitleDialog")
    }
    fun showAddLocationDialog(v: View){
        val dialog = NoteLocationDialog()
        dialog.show(supportFragmentManager, "NoteLocationDialog")
    }
    fun showAddNoteDescriptionDialog(v: View){
        val dialog = NoteDescriptionDialog()
        dialog.show(supportFragmentManager, "NoteDescriptionDialog")
    }

    private fun saveData() {
        if (title == "" || description == "" || category == "" || location == ""){
            Toast.makeText(this, "All fields are required!",Toast.LENGTH_LONG).show()
        } else {
            val newNote = Note.create()
            newNote.title = title
            newNote.description = description
            newNote.location = location
            newNote.category = category
            val newItem = mDatabase.child("notes").push()
            newNote.id = newItem.key
            newItem.setValue(newNote)
            Toast.makeText(this, "New note saved: " + newNote.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
