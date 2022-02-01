package com.dladukedev.wordle.game.data

import javax.inject.Inject
import javax.inject.Singleton

interface WordBank {
    fun getRandomWord(): String
    fun isWordInBank(word: String): Boolean
}

@Singleton
class WordBankImpl @Inject constructor(private val assetManagerDataSource: AssetManagerDataSource):
    WordBank {
    private val words by lazy {
        assetManagerDataSource.getWordBank()
    }

    override fun getRandomWord(): String {
        return words.random()
    }

    override fun isWordInBank(word: String): Boolean {
        return words.contains(word.lowercase())
    }

}