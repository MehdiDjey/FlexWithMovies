package com.druide.flexwithmovies.utils

import android.content.Context
import android.util.Log
import com.druide.flexwithmovies.model.Movies
import kotlinx.serialization.json.Json
import java.io.IOException


fun getMovies(context: Context): Movies {

    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("movies.json")
            .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
        Log.e("TAG", "getCountryCode: $ioException", )
    }

    return Json.decodeFromString(Movies.serializer(), jsonString)
}


