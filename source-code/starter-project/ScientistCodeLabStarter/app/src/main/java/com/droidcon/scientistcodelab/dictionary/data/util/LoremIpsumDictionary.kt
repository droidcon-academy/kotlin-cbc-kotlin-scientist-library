package com.droidcon.scientistcodelab.dictionary.data.util

import android.content.Context
import android.util.Log
import com.droidcon.scientistcodelab.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class LoremIpsumDictionary(private val context: Context) {

    var wordList = mutableListOf<String>()

    suspend fun loadWorldList() {
        val reader = context.resources.openRawResource(R.raw.loremipsum).bufferedReader()
        try {
            var dictionaryString = ""
            reader.use {
                dictionaryString = it.readLine()
            }
            wordList.addAll(dictionaryString.split(" "))
            Log.d("LoremDictionary", "word count ${wordList.size}")
        } catch (e: Exception) {
            Log.d("LoremDictionary", "Failed to load loremipsum")
        } finally {
            withContext(Dispatchers.IO) {
                reader.close()
            }
        }
    }

    fun generateJsonString(): String {
        return Json.encodeToString(wordList)
    }

}