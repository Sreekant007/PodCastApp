package com.shrikant.voicerecordingapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recording")
class RecordingModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "isPlaying") var isPlaying: Boolean,
    @ColumnInfo(name = "recordingDuration") var recordingDuration: String,
    @ColumnInfo(name = "recordingPlayingTime") var recordingPlayingTime: Int = 0,
    @ColumnInfo(name = "createdDate") val createdDate: String,
)