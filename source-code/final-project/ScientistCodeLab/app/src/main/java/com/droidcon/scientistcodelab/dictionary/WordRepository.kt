package com.droidcon.scientistcodelab.dictionary

import com.droidcon.scientistcodelab.dictionary.data.DictionaryDataSource

class WordRepository(private val datasource: DictionaryDataSource) {
    suspend fun getAllWords(): List<String> = datasource.getAllWords()
}