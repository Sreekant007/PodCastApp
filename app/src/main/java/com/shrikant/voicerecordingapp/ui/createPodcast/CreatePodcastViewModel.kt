package com.shrikant.voicerecordingapp.ui.createPodcast

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shrikant.voicerecordingapp.data.db.RecordingRepository
import com.shrikant.voicerecordingapp.data.model.RecordingModel
import com.shrikant.voicerecordingapp.utils.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePodcastViewModel @Inject constructor(private val recordingRepository: RecordingRepository, application: Application) : AndroidViewModel(application) {

    private val _recordingList = MutableLiveData<List<RecordingModel>>()
    val recordingList: LiveData<List<RecordingModel>>
        get() = _recordingList

    private val context = getApplication<Application>().applicationContext
    private val mediaPlayerManager = MediaPlayerManager()

    fun getRecordingList() {
        val recording = recordingRepository.getRecording()
        _recordingList.postValue(recording)
    }

    fun playMedia(recordingModel: RecordingModel) {
        if (recordingModel.isPlaying == true) {
            stopMedia()
            mediaPlayerManager.playMedia(recordingModel.title, context)
        } else {
            mediaPlayerManager.playMedia(recordingModel.title, context)

        }
    }

    fun updateRecordingPlayStatus(recordingModel: RecordingModel) {
        recordingRepository.updateRecording(recordingModel)
    }


    fun stopMedia() {
        mediaPlayerManager.releaseMediaPlayer()
    }


}