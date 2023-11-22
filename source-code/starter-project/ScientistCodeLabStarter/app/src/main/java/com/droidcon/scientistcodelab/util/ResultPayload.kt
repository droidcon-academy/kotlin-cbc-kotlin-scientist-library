package com.droidcon.scientistcodelab.util

import com.github.spoptchev.scientist.Result
import com.github.spoptchev.scientist.isSuccess

data class ResultPayload(
    val areResultsMatching: Boolean,
    val isControlSuccessful: Boolean,
    val isCandidateSuccessful: Boolean,
    val controlDuration: Long,
    val candidateDuration: Long
)

fun Result<*, *>.toResultPayload(): ResultPayload {
    return ResultPayload(
        matched,
        controlObservation.outcome.isSuccess(),
        candidateObservations.first().outcome.isSuccess(),
        controlObservation.duration.millis(),
        candidateObservations.first().duration.millis()
    )
}