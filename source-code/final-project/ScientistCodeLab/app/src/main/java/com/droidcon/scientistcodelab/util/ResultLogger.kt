package com.droidcon.scientistcodelab.util

import android.util.Log
import com.droidcon.scientistcodelab.connections.GetFollowersExperiment
import com.github.spoptchev.scientist.Result
import com.github.spoptchev.scientist.isSuccess

object ResultLogger {
    fun log(result: Result<*, *>) {
        val resultMessage = if (result.matched) "Matching" else "Mismatch"
        Log.d(GetFollowersExperiment.experimentName, "${result.experimentName} : $resultMessage")
        Log.d(
            GetFollowersExperiment.experimentName,
            "Control isSuccess: ${result.controlObservation.outcome.isSuccess()}"
        )
        Log.d(
            GetFollowersExperiment.experimentName,
            "Candidate isSuccess: ${result.candidateObservations.first().outcome.isSuccess()}"
        )
        Log.d(
            GetFollowersExperiment.experimentName,
            "Control Duration: ${result.controlObservation.duration.millis()}ms"
        )
        Log.d(
            GetFollowersExperiment.experimentName,
            "Candidate Duration: ${result.candidateObservations.first().duration.millis()}ms"
        )
    }
}