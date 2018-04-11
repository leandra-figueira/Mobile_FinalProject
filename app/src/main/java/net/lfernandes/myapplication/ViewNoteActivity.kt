package net.lfernandes.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewNoteActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        id = intent.getStringExtra("id")
        val title = intent.getStringExtra("title")
        val category = intent.getStringExtra("category")
        val description = intent.getStringExtra("description")
        val location = intent.getStringExtra("location")

        val txtTitle = findViewById<TextView>(R.id.txtTitle) as TextView
        val txtCategory = findViewById<TextView>(R.id.txtCategory) as TextView
        val txtDescription = findViewById<TextView>(R.id.txtDescription) as TextView
        val txtLocation = findViewById<TextView>(R.id.txtLocation) as TextView
        // onclick handled on the view side
        val btnDelete = findViewById<Button>(R.id.btnDeleteNote) as Button

        txtTitle.text = title
        txtDescription.text = description
        txtLocation.text = location
        txtCategory.text = category

        btnDelete.setOnClickListener{
            showNoteDeleteDialog(View(this))
        }
    }

    fun showNoteDeleteDialog(v: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_delete_note, null)
        val intent = Intent(this, ListNotesActivity::class.java)

        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, whichButton ->
            removeItem(id)
            startActivity(intent)
        })

        dialogBuilder.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, whichButton ->
            //pass
        })

        val b = dialogBuilder.create()
        b.show()
    }

    fun removeItem(noteId: String){
        mDatabase = FirebaseDatabase.getInstance().getReference("notes")
        mDatabase.child(noteId).removeValue()
    }
}
