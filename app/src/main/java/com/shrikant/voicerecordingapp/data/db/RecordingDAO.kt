package com.shrikant.voicerecordingapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shrikant.voicerecordingapp.data.model.RecordingModel


@Dao
interface RecordingDAO {


    @Insert
    fun addRecording(recordingModel: RecordingModel)

    @Query("SELECT * FROM recording ORDER BY id DESC")
    fun getAllRecording(): List<RecordingModel>

    @Update
    fun updateRecordingStatus(recordingModel: RecordingModel)

}