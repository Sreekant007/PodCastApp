package com.shrikant.voicerecordingapp.di

import android.app.Application
import com.shrikant.voicerecordingapp.data.db.RecordingDAO
import com.shrikant.voicerecordingapp.data.db.RecordingDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {


    @Singleton
    @Provides
    fun provideRoomDb(context: Application): RecordingDb {
        return RecordingDb.getAppDb(context)
    }

    @Singleton
    @Provides
    fun provideRoomDAO(recordingDb: RecordingDb): RecordingDAO {
        return recordingDb.getRecordingDAO()
    }

}