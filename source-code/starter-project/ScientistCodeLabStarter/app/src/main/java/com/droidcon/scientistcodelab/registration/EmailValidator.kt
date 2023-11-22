package com.droidcon.scientistcodelab.registration

interface EmailValidator {

    fun isValidEmail(email: String): Boolean

}