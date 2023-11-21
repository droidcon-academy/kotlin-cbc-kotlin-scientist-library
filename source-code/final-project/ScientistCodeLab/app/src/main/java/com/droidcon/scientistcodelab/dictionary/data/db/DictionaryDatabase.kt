package com.droidcon.scientistcodelab.dictionary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntry::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordEntryDao
}