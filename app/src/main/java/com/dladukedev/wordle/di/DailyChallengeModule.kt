package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.data.DailyChallengeBank
import com.dladukedev.wordle.game.data.DailyChallengeBankImpl
import com.dladukedev.wordle.game.data.DailyChallengeGameStateRepositoryImpl
import com.dladukedev.wordle.game.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DailyChallengeModule {
    @Binds
    abstract fun bindDailyChallengeBank(impl: DailyChallengeBankImpl): DailyChallengeBank

    @Binds
    abstract fun bindDailyChallengeGameStateRepository(impl: DailyChallengeGameStateRepositoryImpl): DailyChallengeGameStateRepository

    @Binds
    abstract fun bindGetDailyChallengeDateOffsetUseCase(impl: GetDailyChallengeDateOffsetUseCaseImpl): GetDailyChallengeDateOffsetUseCase

    @Binds
    abstract fun bindGetDailyChallengeGameNameUseCase(impl: GetDailyChallengeGameNameUseCaseImpl): GetDailyChallengeGameNameUseCase

    @Binds
    abstract fun bindGetDailyChallengeWordInitialState(impl: GetDailyChallengeWordInitialStateImpl): GetDailyChallengeWordInitialState

    @Binds
    abstract fun bindGetDateForDailyChallengeUseCase(impl: GetDateForDailyChallengeUseCaseImpl): GetDateForDailyChallengeUseCase

    @Binds
    abstract fun bindSaveDailyChallengeStateUseCase(impl: SaveDailyChallengeStateUseCaseImpl): SaveDailyChallengeStateUseCase

    @Binds
    abstract fun bindSaveDailyChallengeStatisticsUseCase(impl: SaveDailyChallengeStatisticsUseCaseImpl): SaveDailyChallengeStatisticsUseCase

    @Binds
    abstract fun bindGetDailyChallengeStatisticsSummaryUseCase(impl: GetDailyChallengeStatisticsSummaryUseCaseImpl): GetDailyChallengeStatisticsSummaryUseCase
}