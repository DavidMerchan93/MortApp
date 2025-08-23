package com.davidmerchan.domain.di

import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.domain.useCase.GetAllCharactersUseCase
import com.davidmerchan.domain.useCase.getAllCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetAllCharactersUseCase(charactersRepository: CharactersRepository): GetAllCharactersUseCase {
        return GetAllCharactersUseCase { getAllCharacters(charactersRepository) }
    }
}
