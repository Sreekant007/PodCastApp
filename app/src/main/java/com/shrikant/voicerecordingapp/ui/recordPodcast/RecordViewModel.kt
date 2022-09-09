package com.shrikant.voicerecordingapp.ui.recordPodcast

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shrikant.voicerecordingapp.data.db.RecordingRepository
import com.shrikant.voicerecordingapp.data.model.RecordingModel
import com.shrikant.voicerecordingapp.utils.MediaPlayerManager
import com.shrikant.voicerecordingapp.utils.MediaRecorderManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(private val recordingRepository: RecordingRepository, application: Application) : AndroidViewModel(application) {

    lateinit var recordingList: MutableLiveData<List<RecordingModel>>
    private val _isTimerStared = MutableLiveData<Boolean>()
    val isTimerStared: LiveData<Boolean>
        get() = _isTimerStared

    private val _isMediaPLaying = MutableLiveData<Boolean>()
    val isMediaPLaying: LiveData<Boolean>
        get() = _isMediaPLaying


    private val context = getApplication<Application>().applicationContext

    private val mediaRecorderManager = MediaRecorderManager()
    private val mediaPlayerManager = MediaPlayerManager()


    fun insertRecording(recordingModel: RecordingModel) {
        recordingRepository.addRecording(recordingModel)
    }

    fun startRecoding(fileName: String) {
        mediaRecorderManager.startRecording(context, fileName)
        _isTimerStared.postValue(true)
    }

    fun stoprecording(fileName: String) {
        mediaRecorderManager.stopRecording()
        _isTimerStared.postValue(false)
    }

    fun playMedia(filName: String) {
        _isMediaPLaying.postValue(true)
        mediaPlayerManager.playMedia(filName, context)
    }

    fun stopMedia() {
        _isMediaPLaying.postValue(false)
        mediaPlayerManager.releaseMediaPlayer()
    }


}