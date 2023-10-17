package com.example.lab6
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var itemsList = ArrayList<Item>()
    private lateinit var con: SQLiteDatabase;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = SQLiteHelper(this);
        con = db.readableDatabase
        getItems()
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
            val isDelete : Boolean = data?.getBooleanExtra("isDelete", false) ?: false
            val cv = ContentValues()
            cv.put("kind", item.kind)
            cv.put("title", item.title)
            cv.put("price", item.price)
            cv.put("weight", item.weight)
            cv.put("photo", item.photo)
            if (index != -1)
            {
                itemsList[index] = item
                cv.put("id", item.id)
                con.update("items", cv, "id=?", arrayOf(item.id.toString()))

            }
            else
            {
                itemsList.add(item)
                con.insert("items", null, cv)
            }
            if(isDelete){
                itemsList.remove(item)
                con.delete("items", "id=?",arrayOf(item.id.toString()))
            }
            val listView: ListView = findViewById(R.id.listItems)
            (listView.adapter as ItemAdapter).notifyDataSetChanged()
        }

    }

    private fun getItems() {
        val cursor = con.query("items",
            arrayOf("id", "kind", "title", "price","weight", "photo"),
            null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val s = Item()
            s.id = cursor.getInt(0)
            s.kind = cursor.getString(1)
            s.title = cursor.getString(2)
            s.price = cursor.getDouble(3)
            s.weight = cursor.getDouble(4);
            s.photo = cursor.getString(5)
            itemsList.add(s)
            cursor.moveToNext()
        }
        cursor.close()
    }






}