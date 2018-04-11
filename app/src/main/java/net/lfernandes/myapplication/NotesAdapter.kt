package net.lfernandes.myapplication

import android.content.Context
import android.os.Build.VERSION_CODES.N
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import org.w3c.dom.Text


class NotesAdapter(context: Context, NotesList: MutableList<Note>) : BaseAdapter(), Filterable {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = NotesList
    private val mFilter = ItemFilter()
    private var filteredList = NotesList

    private class ListRowHolder(row: View?) {
        val title: TextView = row!!.findViewById<View>(R.id.tv_row_title) as TextView
        val location: TextView = row!!.findViewById<TextView>(R.id.tv_row_location) as TextView
        val category: TextView = row!!.findViewById<TextView>(R.id.tv_row_category) as TextView
        val description: TextView = row!!.findViewById<TextView>(R.id.tv_row_description) as TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val id: String = filteredList[position].id as String
        val title: String = filteredList[position].title as String
        val location: String = filteredList[position].location as String
        val category: String = filteredList[position].category as String
        val description: String = filteredList[position].description as String

        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.notes_row, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.title.text = title
        vh.location.text = location
        vh.category.text = category
        vh.description.text = description
        return view
    }

    override fun getItem(index: Int): Any {
        return filteredList[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    private inner class ItemFilter : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint.toString().toLowerCase()
            val results = FilterResults()
            val list = itemList
            val count = list.size
            val nlist = mutableListOf<Note>()
            var filterableString:String?
            for (i in 0 until count)
            {
                // this concatenation allows me to search on these fields
                filterableString = list[i].title + list[i].location + list[i].description
                if (filterableString!!.toLowerCase().contains(filterString))
                {
                    nlist.add(list[i])
                }
            }
            results.values = nlist
            results.count = nlist.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredList = results!!.values as MutableList<Note>
            notifyDataSetChanged()
        }

    }
}