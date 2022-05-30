package com.druide.flexwithmovies.utils

import java.io.InputStreamReader

class TestMockResponseFileReader(path: String) {

    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}