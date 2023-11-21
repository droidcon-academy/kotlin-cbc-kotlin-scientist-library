package com.droidcon.scientistcodelab.registration

interface EmailValidator {

    fun isValidEmail(email: String): Boolean

//    fun isValidPassword(password: String): Boolean

    /*

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password)
    }
     */
}