package com.droidcon.scientistcodelab.dictionary.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordEntryDao {
    @Query("SELECT * FROM word_entry")
    suspend fun getAll(): List<WordEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordEntry>)
}