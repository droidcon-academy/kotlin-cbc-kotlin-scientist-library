package com.droidcon.scientistcodelab.dictionary

import android.content.Context
import com.droidcon.scientistcodelab.dictionary.data.RoomDbDataSource
import com.droidcon.scientistcodelab.dictionary.data.SharedPreferenceDataSource
import com.droidcon.scientistcodelab.dictionary.data.util.LoremIpsumDictionary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionaryScenario(context: Context) {

    private val loremIpsumDictionary = LoremIpsumDictionary(context)
    val sharedPrefDataSource: SharedPreferenceDataSource = SharedPreferenceDataSource(context)
    val roomDbDataSource: RoomDbDataSource = RoomDbDataSource(context)

    fun setup() {
        CoroutineScope(Dispatchers.IO).launch {
            if (loremIpsumDictionary.wordList.isEmpty()) {
                loremIpsumDictionary.loadWorldList()
            }
            if (!sharedPrefDataSource.isInitialized()) {
                sharedPrefDataSource.insertDictionary(loremIpsumDictionary.generateJsonString())
            }
            if (!roomDbDataSource.isInitialized()) {
                roomDbDataSource.insertDictionary(loremIpsumDictionary.wordList)
            }
        }
    }
}