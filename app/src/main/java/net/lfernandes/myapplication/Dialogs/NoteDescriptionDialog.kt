package net.lfernandes.myapplication.Dialogs

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import net.lfernandes.myapplication.CustonListAdapter
import net.lfernandes.myapplication.NewNoteActivity
import net.lfernandes.myapplication.R

/**
 * Created by Leandra Fernandes on 2018-03-21.
 */
class NoteDescriptionDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val newView = inflater!!.inflate(R.layout.dialog_note_description, container, false)
        val dismiss = newView.findViewById<Button>(R.id.dlg_btnCancel) as Button

        dismiss.setOnClickListener{
            dismiss()
        }

        val enter = newView.findViewById<Button>(R.id.dlg_btnEnter) as Button
        val txtDescription = newView.findViewById<TextView>(R.id.dlg_tvNoteDescription) as TextView

        enter.setOnClickListener{
            (activity as NewNoteActivity).description = txtDescription.text.toString()
            // gets the information from the Activity to assign the data to the text view on the activity
            val noteDescription = activity.findViewById<TextView>(R.id.tvContent) as TextView
            noteDescription.text = (activity as NewNoteActivity).description
            dismiss()
        }
        return newView
    }
}