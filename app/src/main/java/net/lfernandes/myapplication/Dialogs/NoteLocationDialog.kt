package net.lfernandes.myapplication.Dialogs
import net.lfernandes.myapplication.Dialogs.NoteTitleDialog
import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_new_note.*
import net.lfernandes.myapplication.Dialogs.NoteLocationDialog
import net.lfernandes.myapplication.NewNoteActivity
import net.lfernandes.myapplication.R
import org.w3c.dom.Text

/**
 * Created by Leandra Fernandes on 2018-03-16.
 */
class NoteLocationDialog: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val newView = inflater!!.inflate(R.layout.dialog_note_location, container, false)
        val dismiss = newView.findViewById<Button>(R.id.dlg_btnCancel) as Button

        dismiss.setOnClickListener {
            dismiss()
        }

        val enter = newView.findViewById<Button>(R.id.dlg_btnEnter) as Button
        val txtAddress = newView.findViewById<TextView>(R.id.dlg_etAddress) as TextView
        val txtCity = newView.findViewById<TextView>(R.id.dlg_etCity) as TextView
        val txtProvince = newView.findViewById<TextView>(R.id.dlg_etProvince) as TextView
        val txtCountry = newView.findViewById<TextView>(R.id.dlg_etCountry) as TextView

        var address = " "
        var city = " "
        var province = " "
        var country = " "

        enter.setOnClickListener {

            if (txtAddress.text.toString() != null) { address = txtAddress.text.toString() + " " }
            if (txtCity.text.toString() != null) { city = txtCity.text.toString() + " " }
            if (txtProvince.text.toString() != null) { province = txtProvince.text.toString() + " " }
            if (txtCountry.text.toString() != null) { country = txtCountry.text.toString() }

            val completeAddress = address + city + province + country
            (activity as NewNoteActivity).location = completeAddress
            val noteLocation = activity.findViewById<TextView>(R.id.tvLocation) as TextView
            noteLocation.text = (activity as NewNoteActivity).location
            dismiss()
        }
        return newView
    }
}