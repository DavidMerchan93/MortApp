package com.davidmerchan.database.di

import android.content.Context
import androidx.room.Room
import com.davidmerchan.database.MortDatabase
import com.davidmerchan.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMortDatabase(
        @ApplicationContext context: Context,
    ): MortDatabase {
        return Room.databaseBuilder(
            context,
            MortDatabase::class.java,
            MortDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideCharacterDao(database: MortDatabase): CharacterDao {
        return database.characterDao()
    }
}
