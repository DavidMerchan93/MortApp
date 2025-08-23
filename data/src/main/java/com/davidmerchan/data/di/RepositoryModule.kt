package com.davidmerchan.data.di

import com.davidmerchan.data.repository.CharactersRepositoryImpl
import com.davidmerchan.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(): CharactersRepository = CharactersRepositoryImpl()
}
