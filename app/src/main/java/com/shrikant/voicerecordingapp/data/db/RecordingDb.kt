package com.shrikant.voicerecordingapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shrikant.voicerecordingapp.data.model.RecordingModel

@Database(entities = [RecordingModel::class], version = 1, exportSchema = false)
abstract class RecordingDb : RoomDatabase() {

    abstract fun getRecordingDAO(): RecordingDAO

    companion object {
        private var dbInstantce: RecordingDb? = null

        fun getAppDb(context: Context): RecordingDb {
            if (dbInstantce == null) {
                dbInstantce = Room.databaseBuilder<RecordingDb>(context.applicationContext, RecordingDb::class.java, "recording")
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstantce!!
        }
    }
}