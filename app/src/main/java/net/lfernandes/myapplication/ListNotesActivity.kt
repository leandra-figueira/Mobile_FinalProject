package net.lfernandes.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class ListNotesActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    var NotesList: MutableList<Note>? = null
    lateinit var adapter: NotesAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        mDatabase = FirebaseDatabase.getInstance().reference

        listViewItems = findViewById<View>(R.id.items_list) as ListView
        val searchView = findViewById<TextView>(R.id.svSearch) as SearchView
        val tvHome = findViewById<TextView>(R.id.tvHome) as TextView

        NotesList = mutableListOf()
        adapter = NotesAdapter(this, NotesList!!)

        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)
        listViewItems!!.adapter = adapter

         listViewItems!!.isTextFilterEnabled()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                adapter.notifyDataSetChanged()
                return true
            }
        })

        listViewItems!!.setOnItemClickListener{
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->

            val selectedNote = NotesList!![position]
            val intent = Intent(this, ViewNoteActivity::class.java)
            intent.putExtra("id", selectedNote.id.toString())
            intent.putExtra("title", selectedNote.title)
            intent.putExtra("category", selectedNote.category)
            intent.putExtra("location", selectedNote.location)
            intent.putExtra("description", selectedNote.description)
            startActivity(intent)
        }

        tvHome.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            addDataToList(dataSnapshot)
            adapter.notifyDataSetChanged()
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.child("notes").children.iterator()
        while (items.hasNext()) {
            val currentItem = items.next()
            val note = Note.create()
            val map = currentItem.value as HashMap<String, Any>
            note.id = currentItem.key
            note.title = map["title"] as String?
            note.location = map["location"] as String?
            note.category = map["category"] as String?
            note.description = map["description"] as String?

            // add to the list
            NotesList!!.add(note);
        }
        // informs adapter something changed
        adapter.notifyDataSetChanged()
    }
}