package com.droidcon.scientistcodelab.dictionary.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_entry")
data class WordEntry(@PrimaryKey val id: Long, val word: String)