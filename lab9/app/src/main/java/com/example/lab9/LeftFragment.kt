package com.example.lab9
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LeftFragment(projectList: ArrayList<Project>): Fragment() {

    private val projectList = projectList
    private lateinit var mainContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.left_fragment, container, false)
        val projectListView : ListView = view.findViewById(R.id.project_list)
        val adapter = ArrayAdapter(requireContext(), R.layout.project_item, projectList)
        projectListView.adapter = adapter

        projectListView.setOnItemClickListener { parent, view, position, id ->
            (mainContext as OnDataListener).onData(position)
        }
        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }


}