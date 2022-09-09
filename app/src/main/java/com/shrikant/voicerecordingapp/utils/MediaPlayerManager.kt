package com.shrikant.voicerecordingapp.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.media.MediaPlayer
import android.os.Environment
import android.util.Log
import java.io.File

class MediaPlayerManager {
    private var mediaPlayer = MediaPlayer()

    fun playMedia(fileName: String, context: Context) {
        val path = getFilePath(fileName, context)
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {

            }
        } catch (e: Exception) {
            Log.d(TAG, "Error in playing = " + e.message)
        }
    }

    fun releaseMediaPlayer() {
        try {
            mediaPlayer.stop()
            mediaPlayer.release()
        } catch (e: Exception) {
            Log.d(TAG, "Error in stop playing = " + e)
        }
    }


    private fun getFilePath(title: String, context: Context): String {
        val contextWrapper = ContextWrapper(context)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val file = File(documentDirectory, title + ".3gp")
        return file.path
    }


}