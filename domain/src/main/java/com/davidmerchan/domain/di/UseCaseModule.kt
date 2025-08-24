package com.davidmerchan.domain.di

import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.domain.useCase.GetAllCharactersUseCase
import com.davidmerchan.domain.useCase.GetCharacterUseCase
import com.davidmerchan.domain.useCase.GetFavoriteCharactersUseCase
import com.davidmerchan.domain.useCase.RemoveCharacterFavoriteUseCase
import com.davidmerchan.domain.useCase.SaveCharacterFavoriteUseCase
import com.davidmerchan.domain.useCase.getAllCharacters
import com.davidmerchan.domain.useCase.getCharacter
import com.davidmerchan.domain.useCase.getFavoriteCharacters
import com.davidmerchan.domain.useCase.removeCharacterFavorite
import com.davidmerchan.domain.useCase.saveCharacterFavorite
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

    @Provides
    @Singleton
    fun provideGetCharacterUseCase(charactersRepository: CharactersRepository): GetCharacterUseCase {
        return GetCharacterUseCase { id -> getCharacter(charactersRepository, id) }
    }

    @Provides
    @Singleton
    fun provideSaveCharacterFavoriteUseCase(
        charactersRepository: CharactersRepository
    ): SaveCharacterFavoriteUseCase {
        return SaveCharacterFavoriteUseCase { id ->
            saveCharacterFavorite(
                id,
                charactersRepository
            )
        }
    }

    @Provides
    @Singleton
    fun provideRemoveCharacterFavoriteUseCase(
        charactersRepository: CharactersRepository
    ): RemoveCharacterFavoriteUseCase {
        return RemoveCharacterFavoriteUseCase { id ->
            removeCharacterFavorite(
                id,
                charactersRepository
            )
        }
    }

    @Provides
    @Singleton
    fun provideGetFavoriteCharactersUseCase(
        charactersRepository: CharactersRepository
    ): GetFavoriteCharactersUseCase {
        return GetFavoriteCharactersUseCase { getFavoriteCharacters(charactersRepository) }
    }

}
