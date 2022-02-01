package com.dladukedev.wordle.game.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameResult::class], version = 1)
abstract class WordleDatabase: RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao
}
