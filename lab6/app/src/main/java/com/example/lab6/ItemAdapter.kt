package com.example.lab6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ItemAdapter(context : Context, items: ArrayList<Item>) : BaseAdapter() {

    var ctx : Context = context;
    var objects : ArrayList<Item> = items
    var inflater : LayoutInflater = ctx.
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) {
            view = inflater.inflate(R.layout.listview_layout_item, parent, false)
        }
        val s = objects[position]

        var v = view?.findViewById(R.id.item_title) as TextView
        v.text = s.title
        v = view.findViewById(R.id.item_info) as TextView
        v.text = s.info

        return view
    }
}