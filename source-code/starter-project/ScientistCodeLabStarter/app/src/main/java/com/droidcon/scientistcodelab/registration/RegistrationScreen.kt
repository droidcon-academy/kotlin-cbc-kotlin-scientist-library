package com.droidcon.scientistcodelab.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.scientistcodelab.util.ResultPayload
import com.droidcon.scientistcodelab.util.ResultRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(emailValidator: EmailValidator) {
    var emailField by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isNextButtonEnabled by remember { mutableStateOf(false) }
    var resultPayload by remember { mutableStateOf<ResultPayload?>(null) }

    Column {
        resultPayload?.let {
            Text(text = "Experiment Results", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            ResultRow(it)
        }
        TextField(
            label = { Text("Email") },
            value = emailField,
            onValueChange = { email ->
                emailField = email
                // TODO refactor to start the experiment
                val isValid = emailValidator.isValidEmail(email)
                isEmailValid = isValid
                isNextButtonEnabled = isEmailValid
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Button(
            onClick = { },
            enabled = isNextButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)

        ) {
            Text(text = "Next")
        }
    }
}

