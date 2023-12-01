package com.example.lab9

import android.graphics.Bitmap


class Project(name: String,fullName : String, description: String, ownerName: String, avatar: Bitmap) {
    val name = name
    val fullName = fullName
    val description = description
    val ownerName = ownerName
    val avatar = avatar

    override fun toString(): String {
        return fullName
    }
}