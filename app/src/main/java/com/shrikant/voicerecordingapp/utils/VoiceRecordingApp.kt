package com.shrikant.voicerecordingapp.utils

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VoiceRecordingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
    }
}