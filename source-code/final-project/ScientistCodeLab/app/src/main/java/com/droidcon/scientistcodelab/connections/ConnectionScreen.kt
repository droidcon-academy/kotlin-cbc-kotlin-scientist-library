package com.droidcon.scientistcodelab.connections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.scientistcodelab.util.ResultPayload
import com.droidcon.scientistcodelab.util.ResultRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun FollowerListScreen(
    lifecycleScope: CoroutineScope,
    getFollowersExperiment: GetFollowersExperiment,
    showSnackbar: (String) -> Unit
) {
    val followers = remember { mutableStateOf(listOf<Follower>()) }
    val resultPayload = remember { mutableStateOf<ResultPayload?>(null) }
    LaunchedEffect(true, Dispatchers.IO) {
        getFollowersExperiment.launch(lifecycleScope, {
            followers.value = it
        }, { showSnackbar("Error getting followers list") }) {
            resultPayload.value = it
        }
    }
    FollowerList(followers.value, resultPayload.value)
}

@Composable
fun FollowerList(followers: List<Follower>, resultPayload: ResultPayload?) {
    Column(Modifier.padding(4.dp)) {
        resultPayload?.let {
            Text(text = "Experiment Results", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            ResultRow(resultPayload)
        }
        Text(text = "Followers List", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        followers.forEach { follower ->
            FollowerRow(follower)
        }
    }
}

@Composable
fun FollowerRow(follower: Follower) {
    Text(text = "id: ${follower.id} - ${follower.name}", fontSize = 18.sp)
}