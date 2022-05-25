package com.druide.flexwithmovies.utils


object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
}