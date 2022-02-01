package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.state.DailyChallengeGameActionStub
import com.dladukedev.wordle.game.state.GameActionStub
import com.dladukedev.wordle.game.state.PracticeGameActionStub
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsePracticeActionStub

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UseDailyChallengeActionStub

@InstallIn(ViewModelComponent::class)
@Module
abstract class GameModule {
    @UsePracticeActionStub
    @Binds
    abstract fun bindsPracticeGameActionStub(randomWordRetriever: PracticeGameActionStub): GameActionStub

    @UseDailyChallengeActionStub
    @Binds
    abstract fun bindsDailyChallengeGameActionStub(factory: DailyChallengeGameActionStub): GameActionStub
}