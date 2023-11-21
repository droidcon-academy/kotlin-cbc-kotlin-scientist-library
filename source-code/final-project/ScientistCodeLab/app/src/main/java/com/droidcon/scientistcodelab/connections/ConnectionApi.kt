package com.droidcon.scientistcodelab.connections

import io.reactivex.rxjava3.core.Single

class ConnectionApi {
    fun fetchFollowersSingle(): Single<FollowerResponse> =
        Single.just(getFollowerResponseStub())
    // Single.error(RuntimeException(""))    // simulate error

    @Suppress("RedundantSuspendModifier")
    suspend fun fetchFollowersCR(): FollowerResponse {
        return getFollowerResponseStub()
//         throw RuntimeException("") // simulate error
    }

    private fun getFollowerResponseStub() = FollowerResponse(
        listOf(
            Follower(0L, "Ken"),
            Follower(1L, "Timmy"),
            Follower(2L, "Jane"),
            Follower(3L, "Monaco"),
            Follower(4L, "Nousha"),
            Follower(5L, "Jane"),
        )
    )
}