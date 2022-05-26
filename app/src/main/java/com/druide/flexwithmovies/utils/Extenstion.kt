package com.druide.flexwithmovies.utils

/**
 * get tag of current current instance
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }


fun String.formattedPosterPath(): String {
    return "https://image.tmdb.org/t/p/original/$this"
}

fun String.formattedBackDropPath(): String {
    return "https://image.tmdb.org/t/p/original/$this"
}