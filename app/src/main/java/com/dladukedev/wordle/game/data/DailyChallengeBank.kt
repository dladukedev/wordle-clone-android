package com.dladukedev.wordle.game.data

import javax.inject.Inject
import javax.inject.Singleton

interface DailyChallengeBank {
    fun getAnswer(dateOffset: Int): String
}

@Singleton
class DailyChallengeBankImpl @Inject constructor(private val assetManagerDataSource: AssetManagerDataSource):
    DailyChallengeBank {
    private val answerBank: List<String> by lazy {
        assetManagerDataSource.getDailyAnswers()
    }

    override fun getAnswer(dateOffset: Int): String {
        val index = if(dateOffset >= answerBank.size) {
            dateOffset % answerBank.size
        } else {
            dateOffset
        }

        return answerBank[index]
    }
}