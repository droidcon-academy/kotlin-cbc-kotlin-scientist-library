package com.droidcon.scientistcodelab

import android.app.Application
import com.droidcon.scientistcodelab.dictionary.DictionaryScenario

class ScientistCodelabApplication : Application() {

    lateinit var dictionaryScenario: DictionaryScenario

    override fun onCreate() {
        super.onCreate()
        instance = this
        dictionaryScenario = DictionaryScenario(this)
        dictionaryScenario.setup()
    }

    companion object {
        lateinit var instance: ScientistCodelabApplication
    }
}