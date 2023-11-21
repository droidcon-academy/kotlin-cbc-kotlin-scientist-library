package com.droidcon.scientistcodelab.dictionary.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlinx.serialization.json.Json

class SharedPreferenceDataSource(context: Context) : DictionaryDataSource {

    private val sharedPreferences = context.getSharedPreferences("Dictionary", MODE_PRIVATE)

    override suspend fun getAllWords(): List<String> {
        val dictionaryJson = sharedPreferences.getString(dictionaryKey, "") ?: ""
        return try {
            Json.decodeFromString(dictionaryJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @SuppressLint("ApplySharedPref")
    fun insertDictionary(dictionaryJson: String) {
        sharedPreferences.edit().putString(dictionaryKey, dictionaryJson).commit()
    }

    fun isInitialized(): Boolean = sharedPreferences.contains(dictionaryKey)

    private val dictionaryKey = "loremipsum-dictionary"
}