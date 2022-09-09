package com.shrikant.voicerecordingapp.data.db

import com.shrikant.voicerecordingapp.data.model.RecordingModel
import javax.inject.Inject

class RecordingRepository @Inject constructor(private val recordingDAO: RecordingDAO) {


    fun getRecording(): List<RecordingModel> {
        return recordingDAO.getAllRecording()
    }

    fun addRecording(recordingModel: RecordingModel) {
        recordingDAO.addRecording(recordingModel)
    }

    fun updateRecording(recordingModel: RecordingModel) {
        recordingDAO.updateRecordingStatus(recordingModel)
    }
}
