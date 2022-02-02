package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.data.*
import com.dladukedev.wordle.game.domain.GetShareStringUseCase
import com.dladukedev.wordle.game.domain.GetShareStringUseCaseImpl
import com.dladukedev.wordle.game.domain.StatisticsRepository
import com.dladukedev.wordle.game.state.GuessWordValidator
import com.dladukedev.wordle.game.state.GuessWordValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    @Binds
    abstract fun bindAssetManagerDataSource(impl: AssetManagerDataSourceImpl): AssetManagerDataSource

    @Binds
    abstract fun bindWordBank(impl: WordBankImpl): WordBank

    @Binds
    abstract fun bindGuessWordValidator(impl: GuessWordValidatorImpl): GuessWordValidator

    @Binds
    abstract fun bindDataStoreWrapper(impl: SaveDataStoreImpl): SaveDataStore

    @Binds
    abstract fun bindGetShareStringUseCase(impl: GetShareStringUseCaseImpl): GetShareStringUseCase

    @Binds
    abstract fun bindStatisticsRepository(impl: StatisticsRepositoryImpl): StatisticsRepository

    @Binds
    abstract fun bindPreferencesDataStore(impl: PreferencesDataStoreImpl): PreferencesDataStore
}