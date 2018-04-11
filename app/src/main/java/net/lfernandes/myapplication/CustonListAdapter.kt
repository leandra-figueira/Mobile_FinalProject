package net.lfernandes.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by Leandra Fernandes on 2018-03-17.
 */
public class CustonListAdapter(context: Context): BaseAdapter() {
    private val mContext: Context
    init{
        this.mContext = context
    }
    // four methods required by the custom adapter. Source: https://www.youtube.com/watch?v=EwwdQt3_fFU
    override fun getCount(): Int {
        return 5
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItem(position: Int): Any {
        return "just testing"
    }
    // responsible for rendering out each row
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)
        return rowMain
    }
}