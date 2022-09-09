package com.shrikant.voicerecordingapp.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import com.shrikant.voicerecordingapp.utils.Constant.TAG
import java.io.File

class MediaRecorderManager {

    private lateinit var mediaRecorder: MediaRecorder

    private lateinit var filePath: String


    fun startRecording(context: Context, audioTitle: String) {
        filePath = getFilePath(audioTitle, context)
        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.setOutputFile(filePath)
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: Exception) {
            Log.e(TAG, "Media Recorder Exception =" + e)

        }

    }

    fun stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop()
            } catch (e: Exception) {
                Log.e(TAG, "Media Recorder Exception = " + e)
            }
        }
    }

    fun getFilePath(): String {
        return filePath
    }


    @TargetApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        mediaRecorder?.pause()
    }


    private fun getFilePath(title: String, context: Context): String {
        val contextWrapper = ContextWrapper(context)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val file = File(documentDirectory, title + ".3gp")
        return file.path
    }


    @TargetApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        mediaRecorder?.resume()

    }
}


