package com.droidcon.scientistcodelab.registration

import android.text.TextUtils
import android.util.Patterns

class TextUtilEmailValidator : EmailValidator {
    override fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}