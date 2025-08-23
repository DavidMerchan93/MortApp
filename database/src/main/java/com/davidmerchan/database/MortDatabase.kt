package com.davidmerchan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.database.entities.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MortDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        const val DATABASE_NAME = "mort_database"
    }
}