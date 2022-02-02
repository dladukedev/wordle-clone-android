package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.domain.GetStatsScreenDataUseCase
import com.dladukedev.wordle.game.domain.GetStatsScreenDataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticsModule {
    @Binds
    abstract fun bindGetStatsScreenDataUseCase(impl: GetStatsScreenDataUseCaseImpl): GetStatsScreenDataUseCase
}