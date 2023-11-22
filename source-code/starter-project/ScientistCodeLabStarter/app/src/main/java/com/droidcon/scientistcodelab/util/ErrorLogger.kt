package com.droidcon.scientistcodelab.util

import android.util.Log

class ErrorLogger {
    fun log(message: String?, throwable: Throwable) {
        Log.d("ErrorLogger", "$message | ${throwable.message}")
    }
}