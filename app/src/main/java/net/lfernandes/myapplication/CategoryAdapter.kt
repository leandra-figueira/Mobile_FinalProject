package net.lfernandes.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.gms.internal.vh

/**
 * Created by Leandra Fernandes on 2018-03-26.
 */

class CategoryAdapter(context: Context, CategoriesList: MutableList<Category>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = CategoriesList

    private class ListRowHolder(row: View?) {
        val name: TextView = row!!.findViewById<TextView>(R.id.tv_row_name) as TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val id: String = itemList[position].id as String
        val name: String = itemList[position].name as String

        val view: View
        val viewHolder: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.category_row, parent, false)
            viewHolder = ListRowHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ListRowHolder
        }

        viewHolder.name.text = name
        return view
    }

    override fun getItem(index: Int): Any {
        return itemList[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }
}