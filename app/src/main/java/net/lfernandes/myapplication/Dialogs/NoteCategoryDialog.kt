package net.lfernandes.myapplication.Dialogs

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_new_note.*
import net.lfernandes.myapplication.Category
import net.lfernandes.myapplication.CategoryAdapter
import net.lfernandes.myapplication.NewNoteActivity
import net.lfernandes.myapplication.R

/**
 * Created by Leandra Fernandes on 2018-03-17.
 */
class NoteCategoryDialog : DialogFragment() {

    lateinit var localAdapter: CategoryAdapter
    private var listViewItems: ListView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val newView = inflater!!.inflate(R.layout.dialog_note_categories, container, false)
        val dismiss = newView.findViewById<Button>(R.id.dlg_btnCancel) as Button
        listViewItems = newView.findViewById<View>(R.id.dlg_lvCategories) as ListView

        dismiss.setOnClickListener{
            dismiss()
        }

        localAdapter = (activity as NewNoteActivity).cAdapter
        listViewItems!!.adapter = localAdapter

        listViewItems!!.onItemClickListener = AdapterView.OnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val categorySelected = listViewItems!!.getItemAtPosition(position)
            var categoryObject = categorySelected as Category
            (activity as NewNoteActivity).category = categoryObject.name!!
            (activity as NewNoteActivity).categoryId = categoryObject.id!!
            // gets the information from the Activity to assign the data to the text view on the activity
            val noteCategory = activity.findViewById<TextView>(R.id.tvCategory) as TextView
            noteCategory.text = (activity as NewNoteActivity).category
            dismiss()
        }
        return newView
    }
}