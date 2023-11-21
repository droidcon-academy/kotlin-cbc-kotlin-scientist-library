package com.droidcon.scientistcodelab.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultRow(result: ResultPayload) {
    Column {
        val matchingLabel =
            if (result.areResultsMatching) "Results are Matching" else "Results Mismatched"
        Text(text = matchingLabel)
        Text(text = "Control Errors? ${!result.isControlSuccessful}")
        Text(text = "Candidate Errors? ${!result.isCandidateSuccessful}")
        Text(text = "Control Experiment Duration: ${result.controlDuration}")
        Text(text = "Candidate Experiment Duration: ${result.candidateDuration}")
    }
}