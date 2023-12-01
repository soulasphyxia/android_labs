package com.example.lab9

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment


class RightFragment(position: Int = 0): Fragment() {

    private lateinit var mainContext: Context
    private lateinit var mainActivity: MainActivity
    private var position = position
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.right_fragment, container, false)
        val projectList = mainActivity.projectsList;
        val project = projectList[position]
        view.findViewById<TextView>(R.id.name).text = "Проект:\n" + project.fullName
        view.findViewById<TextView>(R.id.description).text = "Описание:\n" + project.description
        view.findViewById<ImageView>(R.id.avatar).setImageBitmap(project.avatar)


        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
        mainActivity = mainContext as MainActivity
    }





}