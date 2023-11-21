package com.droidcon.scientistcodelab.connections

import com.droidcon.scientistcodelab.util.ResultLogger
import com.droidcon.scientistcodelab.util.ResultPayload
import com.droidcon.scientistcodelab.util.toResultPayload
import com.github.spoptchev.scientist.conduct
import com.github.spoptchev.scientist.experiment
import com.github.spoptchev.scientist.scientist
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GetFollowersExperiment(private val repository: ConnectionsRepository) {

    fun launch(
        experimentScope: CoroutineScope,
        onSuccess: (List<Follower>) -> Unit = { },
        onError: (Throwable) -> Unit = { },
        onPublishResults: (ResultPayload) -> Unit = { }
    ) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        experimentScope.launch(Dispatchers.Default + handler) {
            val scientist = scientist<List<Follower>, Unit> {
                publisher { result ->
                    onPublishResults(result.toResultPayload())
                    ResultLogger.log(result)
                }
            }

            val experiment = experiment<List<Follower>, Unit> {
                name { experimentName }
                catches { e -> e is RuntimeException }
                control {
                    repository.getFollowersSingle()
                        .subscribeOn(Schedulers.io())
                        .blockingGet()
                }
                candidate {
                    runBlocking(Dispatchers.IO) {
                        repository.getFollowersCoroutine()
                    }
                }
            }
            val result = scientist conduct experiment
            onSuccess(result)
        }
    }

    companion object {
        const val experimentName = "get-followers-rx-to-cr-migration"
    }
}