package com.example.lab9


import android.graphics.BitmapFactory
import android.util.Log
import org.json.JSONObject
import java.net.URL

class RequestsHandler {
    private val url = "https://api.github.com/search/repositories?q="
    private val PROJECTS_COUNT = 15

    fun getProjectsStartsWith(sequence: String) : ArrayList<Project>{

        var requestURL = URL(url + sequence).readText()
        val jsonObject = JSONObject(requestURL)
        val jsonArray = jsonObject.getJSONArray("items")
        val projectsList : ArrayList<Project> = ArrayList()
        var project = jsonArray.getJSONObject(0)

        for(i: Int in 0..< PROJECTS_COUNT){
            var project = jsonArray.getJSONObject(i)
            var name = project.get("name").toString()
            var fullName = project.get("full_name").toString()
            var description = project.get("description").toString()
            var owner = project.getJSONObject("owner")
            var ownerName = owner.get("login").toString()
            var avatar = URL(owner.get("avatar_url").toString() + ".png").readBytes()

            val bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.size);


            projectsList.add(Project(name,fullName,description,ownerName,bitmap))
        }

        return projectsList
    }

}