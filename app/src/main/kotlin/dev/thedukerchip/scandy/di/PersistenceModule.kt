package dev.thedukerchip.scandy.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.thedukerchip.scandy.data.ScandyDatabase
import dev.thedukerchip.scandy.data.history.ScannedHistoryDao

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ScandyDatabase {
        return Room.databaseBuilder(context, ScandyDatabase::class.java, "scandy").build()
    }

    @Provides
    fun provideScannedHistoryDao(database: ScandyDatabase): ScannedHistoryDao {
        return database.scannedHistoryDao()
    }
}