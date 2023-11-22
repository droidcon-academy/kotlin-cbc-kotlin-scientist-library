package com.droidcon.scientistcodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.scientistcodelab.connections.ConnectionApi
import com.droidcon.scientistcodelab.connections.ConnectionsRepository
import com.droidcon.scientistcodelab.connections.FollowerListScreen
import com.droidcon.scientistcodelab.dictionary.DictionaryScreen
import com.droidcon.scientistcodelab.dictionary.WordRepository
import com.droidcon.scientistcodelab.registration.RegexEmailValidator
import com.droidcon.scientistcodelab.registration.RegistrationScreen
import com.droidcon.scientistcodelab.ui.theme.ScientistCodeLabTheme
import com.droidcon.scientistcodelab.util.ErrorLogger
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val connectionApi = ConnectionApi()
    private val errorLogger = ErrorLogger()
    private val repository = ConnectionsRepository(connectionApi, errorLogger)
    private val regexEmailValidator = RegexEmailValidator()

    private val wordRepository: WordRepository =
        WordRepository(ScientistCodelabApplication.instance.dictionaryScenario.sharedPrefDataSource)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScientistCodeLabTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val canNavigateUp = remember { mutableStateOf(false) }
                Scaffold(
                    topBar = {
                        MainAppBar(navController, canNavigateUp.value)
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { contentPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "main") {
                            composable("dictionary") {
                                canNavigateUp.value = true
                                DictionaryScreen(wordRepository) { message ->
                                    scope.launch {
                                        snackbarHostState
                                            .showSnackbar(
                                                message = message,
                                                duration = SnackbarDuration.Short
                                            )
                                    }
                                }
                            }
                            composable("followerslist") {
                                canNavigateUp.value = true
                                FollowerListScreen(
                                    repository,
                                ) { message ->
                                    scope.launch {
                                        snackbarHostState
                                            .showSnackbar(
                                                message = message,
                                                duration = SnackbarDuration.Short
                                            )
                                    }
                                }
                            }
                            composable("registration") {
                                canNavigateUp.value = true
                                RegistrationScreen(regexEmailValidator)
                            }
                            composable("main") {
                                canNavigateUp.value = false
                                Column(Modifier.padding(4.dp)) {
                                    Button(onClick = {
                                        navController.navigate("registration")
                                    }) {
                                        Text("Registration List Experiment")
                                    }
                                    Button(onClick = {
                                        navController.navigate("dictionary")
                                    }) {
                                        Text("Dictionary Experiment")
                                    }
                                    Button(onClick = {
                                        navController.navigate("followerslist")
                                    }) {
                                        Text("Follower List Experiment")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainAppBar(navController: NavController, canNavigateUp: Boolean) {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Experiments")
            },
            navigationIcon = {
                if (canNavigateUp) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }
            },
        )
    }
}