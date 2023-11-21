package com.droidcon.scientistcodelab.registration

import java.util.regex.Pattern

class RegexEmailValidator : EmailValidator {
    override fun isValidEmail(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }

    companion object {
        private const val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailPattern: Pattern = Pattern.compile(emailRegex)
    }
}