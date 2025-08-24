package com.davidmerchan.network.di

import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.api.RickAndMortyClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return RickAndMortyClient.create()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyService(httpClient: HttpClient): RickAndMortyApi {
        return RickAndMortyApi(httpClient)
    }
}
