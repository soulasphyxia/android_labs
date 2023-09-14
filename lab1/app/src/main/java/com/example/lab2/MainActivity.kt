package com.example.lab2

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    private lateinit var operationsSpinner: Spinner;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        operationsSpinner = findViewById(R.id.operations_spinner);
        ArrayAdapter.createFromResource(
            this,
            R.array.operations_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            operationsSpinner.adapter = adapter
        }
    }

    fun calculateExpression(view: View){
        val edit1: EditText = findViewById(R.id.number1)
        val n1 = edit1.text.toString().toFloat()

        val edit2: EditText = findViewById(R.id.number2)
        val n2 = edit2.text.toString().toFloat()

        val operation: String = operationsSpinner.selectedItem.toString();

        val result: Float = calculateWithOperation(operation, n1, n2);

        val resultTextView: TextView = findViewById(R.id.result)

        if(result == Float.POSITIVE_INFINITY) {
            resultTextView.text = "Ошибка!"
        }else {
            val resText = resources.getString(R.string.operation_result)
            resultTextView.text = String.format(resText, result);
        }
    }

    private fun calculateWithOperation(operation: String, n1: Float, n2: Float): Float {
        when(operation){
            "+" -> return n1 + n2;
            "-" -> return n1 - n2;
            "*" -> return n1 * n2;
            "/" -> return n1 / n2;
            "^" -> return n1.pow(n2);
        }
        return Float.POSITIVE_INFINITY;
    }
}
