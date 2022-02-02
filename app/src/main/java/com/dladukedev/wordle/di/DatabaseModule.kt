package com.dladukedev.wordle.di

import android.content.Context
import androidx.room.Room
import com.dladukedev.wordle.game.data.db.GameResultDao
import com.dladukedev.wordle.game.data.db.WordleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideWordleDatabase(@ApplicationContext context: Context): WordleDatabase {
        return Room.databaseBuilder(
            context,
            WordleDatabase::class.java,
            "WORDLE_DB"
        ).build()
    }

    @Provides
    fun provideGameResultDao(database: WordleDatabase): GameResultDao {
        return database.gameResultDao()
    }
}
