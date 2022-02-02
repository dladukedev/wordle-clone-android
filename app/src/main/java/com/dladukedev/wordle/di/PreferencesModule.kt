package com.dladukedev.wordle.di

import com.dladukedev.wordle.game.data.PreferencesRepositoryImpl
import com.dladukedev.wordle.game.data.SystemThemeProvider
import com.dladukedev.wordle.game.data.SystemThemeProviderImpl
import com.dladukedev.wordle.game.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    abstract fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    abstract fun bindUpdateIsColorBlindModeUseCase(impl: UpdateIsColorBlindModeUseCaseImpl): UpdateIsColorBlindModeUseCase

    @Binds
    abstract fun bindUpdateIsHardModeUseCase(impl: UpdateIsHardModeUseCaseImpl): UpdateIsHardModeUseCase

    @Binds
    abstract fun bindUpdateColorThemeUseCase(impl: UpdateColorThemeUseCaseImpl): UpdateColorThemeUseCase

    @Binds
    abstract fun bindSubscribeToPreferencesUseCase(impl: SubscribeToPreferencesUseCaseImpl): SubscribeToPreferencesUseCase

    @Binds
    abstract fun bindGetPreferencesUseCase(impl: GetPreferencesUseCaseImpl): GetPreferencesUseCase

    @Binds
    abstract fun bindGetCurrentAppThemeUseCase(impl: GetCurrentAppThemeUseCaseImpl): GetCurrentAppThemeUseCase

    @Binds
    abstract fun bindSystemThemeProvider(impl: SystemThemeProviderImpl): SystemThemeProvider
}