package com.droidcon.scientistcodelab.dictionary.data

import android.content.Context
import androidx.room.Room
import com.droidcon.scientistcodelab.dictionary.data.db.DictionaryDatabase
import com.droidcon.scientistcodelab.dictionary.data.db.WordEntry


class RoomDbDataSource(context: Context) : DictionaryDataSource {

    private val db = Room.databaseBuilder(
        context,
        DictionaryDatabase::class.java, "dictionary-db"
    ).build()

    override suspend fun getAllWords(): List<String> {
        return db.wordDao().getAll().map { it.word }
    }

    suspend fun insertDictionary(wordList: List<String>) {
        var counter = 0L
        db.wordDao().insertAll(wordList.map { WordEntry(counter++, it) })
    }

    suspend fun isInitialized() = getAllWords().isNotEmpty()

}