package com.davidmerchan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.davidmerchan.database.entities.CharacterEntity

/**
 * Data Access Object (DAO) for Character entities.
 *
 * This interface defines the database operations for managing character data in the SQLite
 * database through Room's abstraction layer. The DAO pattern separates data access logic
 * from business logic, providing a clean and maintainable API for database interactions.
 *
 * ## Threading and Coroutines:
 * All database operations are marked as `suspend` functions to ensure they run on
 * background threads. Room automatically handles thread management when called from
 * coroutines, preventing ANR (Application Not Responding) issues.
 *
 * ## SQL Query Optimization:
 * - Uses indexed primary key lookups for single character queries
 * - Boolean comparisons (is_favorite = 1) leverage SQLite's efficient boolean storage
 * - Batch operations for inserting multiple characters improve performance
 *
 * @see CharacterEntity The entity this DAO operates on
 */
@Dao
interface CharacterDao {

    /**
     * Retrieves all characters from the database.
     *
     * @return List of [CharacterEntity] containing all characters in the database.
     *         Returns an empty list if no characters are found.
     */
    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    /**
     * Retrieves a specific character by their unique identifier.
     *
     * @param id The unique identifier of the character to retrieve
     * @return [CharacterEntity] if found, null if no character exists with the given ID.
     *         Nullable return type handles cases where the character doesn't exist.
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    /**
     * Retrieves all characters marked as favorites.
     *
     * @return List of [CharacterEntity] containing only favorite characters.
     *         Returns an empty list if no favorite characters are found.
     */
    @Query("SELECT * FROM characters WHERE is_favorite = 1")
    suspend fun getFavoriteCharacters(): List<CharacterEntity>

    /**
     * Inserts multiple characters into the database in a single transaction.
     *
     * @param characters List of [CharacterEntity] objects to insert into the database.
     *                  Can be an empty list, which results in no operation.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    /**
     * Inserts a single character into the database.
     *
     * @param character The [CharacterEntity] to insert or replace in the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    /**
     * Updates an existing character in the database.
     *
     * @param character The [CharacterEntity] with updated values.
     *                 The ID must match an existing character for the update to occur.
     */
    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    /**
     * Deletes a specific character from the database.
     *
     * @param character The [CharacterEntity] to delete from the database.
     *                 Only the ID field is used for identification.
     */
    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)

    /**
     * Updates the favorite status of a specific character.
     *
     * ## Parameter Details:
     * @param id The unique identifier of the character to update
     * @param isFavorite The new favorite status (true for favorite, false for not favorite).
     *                  Room automatically converts boolean to integer for SQLite storage.
     */
    @Query("UPDATE characters SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(
        id: Int,
        isFavorite: Boolean,
    )

    /**
     * Deletes all characters from the database.
     */
    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}
