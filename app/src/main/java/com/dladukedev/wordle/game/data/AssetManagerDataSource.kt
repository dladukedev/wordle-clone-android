package com.dladukedev.wordle.game.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface AssetManagerDataSource {
    fun getDailyAnswers(): List<String>
    fun getWordBank(): HashSet<String>
}

// TODO: Handle context instance?
// TODO: Running on Main Thread
@Singleton
class AssetManagerDataSourceImpl @Inject constructor(@ApplicationContext private val context: Context):
    AssetManagerDataSource {
    override fun getDailyAnswers(): List<String> {
        return context.assets.open(dailyChallengeAnswerBankFileName).bufferedReader().useLines { it.toList() }
    }

    override fun getWordBank(): HashSet<String> {
        return context.assets.open(wordBankFileName).bufferedReader().useLines { it.toHashSet() }
    }

    companion object {
        private const val dailyChallengeAnswerBankFileName = "answer-bank.txt"
        private const val wordBankFileName = "word-bank.txt"
    }
}