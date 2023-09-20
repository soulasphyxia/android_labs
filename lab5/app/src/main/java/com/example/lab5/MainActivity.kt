package com.example.lab5
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var itemsList = ArrayList<Item>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listView : ListView = findViewById(R.id.listItems)
        listView.adapter = ItemAdapter(this, itemsList)

        listView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("index",i)
            intent.putExtra("item", itemsList[i])

            startActivityForResult(intent,0)
        }

        val fab : View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, ItemActivity::class.java)
            startActivityForResult(intent,0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        {
            val index: Int = data?.getIntExtra("index", -1) ?: -1
            val item: Item = data?.getParcelableExtra("item") ?: Item()

            if (index != -1)
                itemsList[index] = item
            else
                itemsList.add(item)

            val listView: ListView = findViewById(R.id.listItems)
            (listView.adapter as ItemAdapter).notifyDataSetChanged()
        }
    }




}