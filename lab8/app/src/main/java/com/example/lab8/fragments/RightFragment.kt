package com.example.lab8.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lab8.Equation
import com.example.lab8.MainActivity
import com.example.lab8.OnDataListener
import com.example.lab8.R
import com.example.lab8.generators.RandomEquationGenerator
import com.example.lab8.generators.RandomNumbersGenerator

class RightFragment(Operation: Int = 0, Difficulty: Int = 1): Fragment() {

    private lateinit var mainContext: Context

    private val operationNumber: Int = Operation
    private val diff = Difficulty
    private lateinit var answers : ArrayList<Int>;
    private lateinit var equation: Equation;
    private val equationGenerator = RandomEquationGenerator()
    private val randomNumbersGenerator = RandomNumbersGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var operation = when(operationNumber){
            0 -> "+"
            1 -> "-"
            else -> "*"
        }
        equation = equationGenerator.generate(operation,diff)

        val randomNumbers : ArrayList<Int> = randomNumbersGenerator.generateNumbersDependsOnTarget(equation.answer)

        answers = ArrayList()
        answers.add(equation.answer)
        answers.addAll(randomNumbers)
        answers.shuffle()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_right, container, false)
        val equationView = view?.findViewById<TextView>(R.id.equationView)
        equationView?.text = equation.stringValue

        val answersListView = view?.findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(requireContext(), R.layout.my_list_item, answers)
        answersListView?.adapter = adapter

        answersListView?.setOnItemClickListener { parent, view, position, id ->
            val selectedAnswer = answers[position]
            val isCorrectAnswer : Boolean  = selectedAnswer == equation.answer
            (mainContext as OnDataListener).onData(operationNumber)
            (mainContext as MainActivity).updateTextViews(isCorrectAnswer)

        }

        return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }





}