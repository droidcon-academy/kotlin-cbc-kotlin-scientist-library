package com.droidcon.scientistcodelab.dictionary

import com.droidcon.scientistcodelab.dictionary.data.RoomDbDataSource
import com.droidcon.scientistcodelab.dictionary.data.SharedPreferenceDataSource
import com.droidcon.scientistcodelab.util.ResultLogger
import com.droidcon.scientistcodelab.util.ResultPayload
import com.droidcon.scientistcodelab.util.toResultPayload
import com.github.spoptchev.scientist.conduct
import com.github.spoptchev.scientist.experiment
import com.github.spoptchev.scientist.scientist
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DictionaryExperiment(
    sharedPrefDataSource: SharedPreferenceDataSource,
    roomDbDataSource: RoomDbDataSource
) {
    private val sharedPrefRepo = WordRepository(sharedPrefDataSource)
    private val roomDbRepo = WordRepository(roomDbDataSource)

    fun launch(
        onSuccess: (List<String>) -> Unit, onError: (Throwable) -> Unit,
        onPublishResults: (ResultPayload) -> Unit = { }
    ) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        CoroutineScope(Dispatchers.Default + handler).launch {
            val scientist = scientist<List<String>, Unit> {
                publisher { result ->
                    onPublishResults(result.toResultPayload())
                    ResultLogger.log(result)

                }
            }

            val experiment = experiment<List<String>, Unit> {
                name { experimentName }
                catches { e -> e is RuntimeException }
                control {
                    runBlocking(Dispatchers.IO) {
                        sharedPrefRepo.getAllWords()
                    }
                }
                candidate {
                    runBlocking(Dispatchers.IO) {
                        roomDbRepo.getAllWords()
                    }
                }
            }
            val result = scientist conduct experiment
            onSuccess(result)
        }
    }

    companion object {
        const val experimentName = "dictionary-experiment"
    }
}