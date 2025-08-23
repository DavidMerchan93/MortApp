package com.davidmerchan.data.di

import com.davidmerchan.data.repository.CharactersRepositoryImpl
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharactersRepository(
        api: RickAndMortyApi
    ): CharactersRepository = CharactersRepositoryImpl(api)
}
