package com.droidcon.scientistcodelab.util

import java.time.Duration

fun Duration.millis(): Long = nano / 1000000L
