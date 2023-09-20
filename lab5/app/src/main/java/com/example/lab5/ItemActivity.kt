package com.example.lab5

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar

class ItemActivity : AppCompatActivity() {

    private var index : Int = 0
    private lateinit var item: Item


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent

        index = intent?.getIntExtra("index", -1) ?: 1

        item = intent?.getParcelableExtra("item", Item::class.java) ?: Item()



        val editTitle = findViewById<EditText>(R.id.title)
        editTitle.setText(item.title)
        val editKind = findViewById<EditText>(R.id.kind)
        editKind.setText(item.kind)
        val editWeight = findViewById<EditText>(R.id.weight)
        editWeight.setText(item.weight.toString())
        val editPrice = findViewById<EditText>(R.id.price)
        editPrice.setText(item.price.toString())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_item, menu)
        Log.i("onCreateOptionsMenu", "onCreateOptionsMenuITEMACTIVITY")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        if (item.itemId == R.id.action_save) {
            this.item.title = findViewById<EditText>(R.id.title).
            text.toString()
            this.item.kind = findViewById<EditText>(R.id.kind).
            text.toString()
            this.item.weight = findViewById<EditText>(R.id.weight).
            text.toString().toDouble()
            this.item.price = findViewById<EditText>(R.id.price).
            text.toString().toDouble()
            val intent = Intent()
            intent.putExtra("index", index)
            intent.putExtra("item", this.item)
            setResult(Activity.RESULT_OK, intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}