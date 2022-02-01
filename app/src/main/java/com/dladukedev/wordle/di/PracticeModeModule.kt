package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.data.PracticeGameStateRepositoryImpl
import com.dladukedev.wordle.game.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PracticeModeModule {
    @Binds
    abstract fun bindGetPracticeModeGameNameUseCase(impl: GetPracticeModeGameNameUseCaseImpl): GetPracticeModeGameNameUseCase

    @Binds
    abstract fun bindGetPracticeModeInitialStateUseCase(impl: GetPracticeModeInitialStateUseCaseImpl): GetPracticeModeInitialStateUseCase

    @Binds
    abstract fun bindSavePracticeModeStateUseCase(impl: SavePracticeModeStateUseCaseImpl): SavePracticeModeStateUseCase

    @Binds
    abstract fun bindPracticeGameStateRepository(impl: PracticeGameStateRepositoryImpl): PracticeGameStateRepository

    @Binds
    abstract fun bindSavePracticeModeStatisticsUseCase(impl: SavePracticeModeStatisticsUseCaseImpl): SavePracticeModeStatisticsUseCase

    @Binds
    abstract fun bindGetPracticeModeStatisticsSummaryUseCase(impl: GetPracticeModeStatisticsSummaryUseCaseImpl): GetPracticeModeStatisticsSummaryUseCase
}