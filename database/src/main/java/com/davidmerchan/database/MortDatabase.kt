package com.davidmerchan.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.database.entities.CharacterEntity

/**
 * Room Database implementation for the MortApp application.
 *
 * This abstract class serves as the main database holder for the application and defines
 * the database configuration. It extends [RoomDatabase] and acts as the primary access
 * point for the underlying SQLite database.
 *
 * ## Database Configuration:
 * - **Entities**: Contains [CharacterEntity] which represents the characters table
 * - **Version**: Currently version 1, will need to be incremented for schema migrations
 * - **Export Schema**: Set to false to disable schema export for testing environments
 *
 * ## Migration Strategy:
 * When the database schema changes, increment the version number and provide migration
 * paths using Room's migration API to preserve user data during updates.
 *
 * @see CharacterEntity The entity representing characters in the database
 * @see CharacterDao The DAO providing access methods for character data operations
 */
@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class MortDatabase : RoomDatabase() {

    /**
     * Provides access to the Character Data Access Object (DAO).
     *
     * This abstract method returns an instance of [CharacterDao] which contains
     * all the methods for accessing and manipulating character data in the database.
     * Room automatically generates the implementation of this method at compile time.
     *
     * @return [CharacterDao] instance for performing character-related database operations
     */
    abstract fun characterDao(): CharacterDao

    companion object {
        /**
         * The name of the SQLite database file.
         *
         * This constant defines the database name used when creating the Room database
         * instance. The database file will be stored in the app's private database
         * directory with this name.
         *
         */
        const val DATABASE_NAME = "mort_database"
    }
}
