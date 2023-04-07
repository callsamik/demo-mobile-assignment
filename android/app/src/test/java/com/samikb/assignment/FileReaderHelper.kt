package com.samikb.assignment

import java.io.InputStreamReader

class FileReaderHelper(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}