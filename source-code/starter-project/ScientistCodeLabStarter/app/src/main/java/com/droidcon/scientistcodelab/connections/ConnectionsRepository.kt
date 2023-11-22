package com.droidcon.scientistcodelab.connections

import com.droidcon.scientistcodelab.util.ErrorLogger
import io.reactivex.rxjava3.core.Single

data class Follower(val id: Long, val name: String)
data class FollowerResponse(val followers: List<Follower>)

class ConnectionsRepository(
    private val connectionApi: ConnectionApi,
    private val errorLogger: ErrorLogger
) {

    fun getFollowersSingle(): Single<List<Follower>> {
        return connectionApi.fetchFollowersSingle()
            .doOnError { errorLogger.log("FetchFollower error", it) }
            .map { response ->
                response.followers
            }
    }
}