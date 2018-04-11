package net.lfernandes.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        btnInternetConnection.setOnClickListener{
            checkConnection();
        }

        // no need to declare the variable button and  findView by id
        btnNewNote.setOnClickListener {
            // equivalent to Log.d
            println("On click listener is working")

            // create an intent - val is the variable declaration
            val newNoteIntent = Intent(this, NewNoteActivity::class.java)
            startActivity(newNoteIntent)
        }

        btnListNotes.setOnClickListener{
            val newAllNotesIntent = Intent(this, ListNotesActivity::class.java)
            startActivity(newAllNotesIntent)
        }
    }

    private fun checkConnection(){
        val activeNetwork = connectivityManager.getActiveNetworkInfo();
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        val message = if (isConnected) "Connection Detected!" else "No Connection Detected!"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
