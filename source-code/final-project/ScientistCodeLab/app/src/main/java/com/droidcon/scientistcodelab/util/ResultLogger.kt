package com.droidcon.scientistcodelab.util

import android.util.Log
import com.github.spoptchev.scientist.Result
import com.github.spoptchev.scientist.isSuccess

object ResultLogger {
    fun log(result: Result<*, *>) {
        val resultMessage = if (result.matched) "Matching" else "Mismatch"
        Log.d("ExperimentLogger", "${result.experimentName} : $resultMessage")
        Log.d(
            "ExperimentLogger",
            "Control isSuccess: ${result.controlObservation.outcome.isSuccess()}"
        )
        Log.d(
            "ExperimentLogger",
            "Candidate isSuccess: ${result.candidateObservations.first().outcome.isSuccess()}"
        )
        Log.d(
            "ExperimentLogger",
            "Control Duration: ${result.controlObservation.duration.millis()}ms"
        )
        Log.d(
            "ExperimentLogger",
            "Candidate Duration: ${result.candidateObservations.first().duration.millis()}ms"
        )
    }
}