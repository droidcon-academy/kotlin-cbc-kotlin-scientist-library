package com.droidcon.scientistcodelab.dictionary.data

interface DictionaryDataSource {
    suspend fun getAllWords(): List<String>
}