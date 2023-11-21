package com.droidcon.scientistcodelab.registration

import com.droidcon.scientistcodelab.util.ResultLogger
import com.droidcon.scientistcodelab.util.ResultPayload
import com.droidcon.scientistcodelab.util.toResultPayload
import com.github.spoptchev.scientist.conduct
import com.github.spoptchev.scientist.experiment
import com.github.spoptchev.scientist.scientist
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmailValidatorExperiment {

    private val regexEmailValidator = RegexEmailValidator()
    private val textUtilEmailValidator = TextUtilEmailValidator()

    fun launch(
        email: String, onSuccess: (Boolean) -> Unit, onError: (Throwable) -> Unit,
        onPublishResults: (ResultPayload) -> Unit = { }
    ) {
        val handler = CoroutineExceptionHandler { _, exception ->
            onError(exception)
        }
        CoroutineScope(Dispatchers.Default + handler).launch {
            val scientist = scientist<Boolean, Unit> {
                publisher { result ->
                    onPublishResults(result.toResultPayload())
                    ResultLogger.log(result)
                }
            }

            val experiment = experiment<Boolean, Unit> {
                name { experimentName }
                catches { e -> e is RuntimeException }
                control {
                    regexEmailValidator.isValidEmail(email)
                }
                candidate {
                    textUtilEmailValidator.isValidEmail(email)
                }
            }
            val result = scientist conduct experiment
            onSuccess(result)
        }
    }

    companion object {
        const val experimentName = "registration-experiment"
    }
}