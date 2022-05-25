package com.druide.flexwithmovies.utils

/**
 * get tag of current current instance
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }