package com.droidcon.scientistcodelab.dictionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.Dispatchers

@Composable
fun DictionaryScreen(
    wordRepository: WordRepository,
    showSnackbar: (String) -> Unit
) {
    val wordList = remember { mutableStateOf(listOf<String>()) }
    val resultPayload = remember { mutableStateOf<ResultPayload?>(null) }
    LaunchedEffect(true, Dispatchers.IO) {
        try {
            // TODO refactor to start the experiment
            wordList.value = wordRepository.getAllWords()
        } catch (e: Exception) {
            showSnackbar("Error getting dictionary word list")
        }
    }
    DictionaryList(wordList.value, resultPayload.value)
}

@Composable
fun DictionaryList(wordList: List<String>, resultPayload: ResultPayload?) {
    Column {
        resultPayload?.let {
            Text(text = "Experiment Results", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            ResultRow(resultPayload)
        }
        Text(text = "Word List ${wordList.size}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        LazyColumn(
            Modifier
                .padding(4.dp)
        ) {
            items(wordList) {
                WordRow(it)
            }
        }
    }
}

@Composable
fun WordRow(word: String) {
    Text(text = word, fontSize = 18.sp)
}