package com.example.lab6

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context?) :
    SQLiteOpenHelper(context,"items.db", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE IF NOT EXISTS items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kind TEXT, " +
                "title TEXT, " +
                "price DECIMAL, " +
                "weight DECIMAL, " +
                "photo TEXT)");
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}