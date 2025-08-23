package com.davidmerchan.data.di

import com.davidmerchan.data.repository.CharactersRepositoryImpl
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
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
    fun provideCharactersRepository(
        api: RickAndMortyApi,
        database: CharacterDao
    ): CharactersRepository = CharactersRepositoryImpl(api, database)
}
