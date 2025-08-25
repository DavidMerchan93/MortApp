package com.davidmerchan.data.repository

import com.davidmerchan.data.mapper.toDatabaseEntity
import com.davidmerchan.data.mapper.toDomain
import com.davidmerchan.database.dao.CharacterDao
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.CharacterId
import com.davidmerchan.domain.repository.CharactersRepository
import com.davidmerchan.network.api.RickAndMortyApi
import com.davidmerchan.network.api.safeApiCall
import javax.inject.Inject

/**
 * Concrete implementation of the CharactersRepository interface following the Repository pattern.
 *
 * This class serves as the single source of truth for character data by implementing a cache-first
 * strategy that prioritizes local database storage over network requests. It coordinates data access
 * between the local database (Room) and remote API (Rick and Morty API) to provide a seamless
 * offline-first experience.
 *
 * **Architecture Pattern:**
 * - Implements the Repository pattern to abstract data sources from the domain layer
 * - Acts as a mediator between the domain layer and data sources (network and database)
 * - Provides a clean API that hides data source complexity from business logic
 *
 * **Caching Strategy:**
 * - Cache-first approach: Always check local database before making network requests
 * - Automatic cache population: Network responses are automatically stored locally
 * - Refresh support: Allows forcing fresh data retrieval from the network
 * - Persistent favorites: User favorites are managed exclusively through local storage
 *
 * **Data Flow:**
 * 1. Check local database for requested data
 * 2. If data exists and refresh not requested, return cached data
 * 3. If data missing or refresh requested, fetch from network API
 * 4. Store network response in local database for future access
 * 5. Return domain entities mapped from the appropriate data source
 *
 * **Error Handling:**
 * Uses safeApiCall wrapper for network operations to provide consistent error handling
 * and Result-based return types that encapsulate success/failure states.
 *
 * @property api Rick and Morty API service for network data retrieval
 * @property database Room DAO for local data persistence and caching
 */
class CharactersRepositoryImpl
@Inject
constructor(
    private val api: RickAndMortyApi,
    private val database: CharacterDao,
) : CharactersRepository {
    /**
     * Retrieves all characters using a cache-first strategy with optional refresh capability.
     *
     * This method implements the core caching logic of the repository by first checking the local
     * database for existing character data. If data exists and no refresh is requested, it returns
     * the cached data. Otherwise, it fetches fresh data from the network API, updates the cache,
     * and returns the new data.
     *
     * **Caching Logic:**
     * - Always attempt to load from cache first to minimize network usage
     * - Bypass cache when explicit refresh is requested or cache is empty
     * - Automatically populate cache with fresh network data when needed
     * - Preserve data across app restarts through persistent local storage
     *
     * @param isRefreshing Flag indicating whether to bypass cache and fetch fresh data.
     *                     When true, forces a network request regardless of cache state.
     *                     When false, uses cached data if available.
     *
     */
    override suspend fun getAllCharacters(isRefreshing: Boolean): Result<List<Character>> {
        return safeApiCall {
            // First, attempt to load characters from local cache
            // This provides immediate data availability and reduces network dependency
            val characters = database.getAllCharacters().map { it.toDomain() }

            // Determine if we need to fetch fresh data from the network
            // This happens when explicitly refreshing or when cache is empty
            if (isRefreshing || characters.isEmpty()) {
                // Fetch fresh data from the Rick and Morty API
                // The API returns a paginated response with results array
                val results = api.getAllCharacters().results

                // Update the local cache with fresh network data
                // This ensures data is available for offline access and future requests
                database.insertCharacters(
                    results.map { it.toDatabaseEntity() },
                )

                // Return the fresh data mapped to domain entities
                // This provides the most up-to-date character information
                results.map { it.toDomain() }
            } else {
                // Return cached data when no refresh is needed
                // This provides instant data access without network overhead
                characters
            }
        }
    }

    /**
     * Retrieves a specific character by ID using cache-first strategy with lazy loading.
     *
     * This method implements lazy loading by checking the local cache first and only making
     * a network request if the character is not found locally. This approach optimizes performance
     * for characters that may have been previously fetched (e.g., from the all characters list)
     * while ensuring data availability for characters accessed directly by ID.
     *
     * @param id Unique identifier of the character to retrieve.
     *           Must be a valid character ID from the Rick and Morty API.
     *
     * @return Result<Character> Wrapped domain Character entity.
     *         - Success: Contains the requested character (from cache or network)
     *         - Failure: Contains error information if network request fails or character not found
     *
     */
    override suspend fun getCharacter(id: Int): Result<Character> {
        return safeApiCall {
            // First, check if the character already exists in the local cache
            // This avoids unnecessary network requests for previously accessed characters
            val character = database.getCharacterById(id)

            // If character not found in cache, fetch from network and cache the result
            if (character == null) {
                // Fetch the specific character from the Rick and Morty API
                // This provides the most up-to-date character information
                val result = api.getCharacter(id)

                // Store the fetched character in local cache for future access
                // This enables offline access and improves performance for subsequent requests
                database.insertCharacter(result.toDatabaseEntity())

                // Return the freshly fetched character mapped to domain entity
                result.toDomain()
            } else {
                // Return the cached character if found locally
                // This provides instant access without network overhead
                character.toDomain()
            }
        }
    }

    /**
     * Retrieves all characters marked as favorites by the user from local storage.
     *
     * This method provides access to the user's favorite characters collection, which is
     * managed exclusively through local database storage. Unlike other repository methods,
     * this operation is purely local and does not involve network requests, ensuring
     * instant access to favorites regardless of network connectivity.
     *
     * @return List<Character> Direct list of domain Character entities marked as favorites.
     *         Returns empty list if no characters are marked as favorites.
     *         This method never fails as it operates only on local data.
     *
     */
    override suspend fun getFavoriteCharacters(): List<Character> {
        // Query the local database for all characters marked as favorites
        // This is a purely local operation that doesn't depend on network connectivity
        val result = database.getFavoriteCharacters()

        // Map database entities to domain entities for consumption by business logic
        // This maintains clean separation between data layer and domain layer
        return result.map { it.toDomain() }
    }

    /**
     * Marks a character as favorite by updating its favorite status in local storage.
     *
     * This method provides the ability to add characters to the user's favorites collection
     * by updating the character's favorite status in the local database. The operation is
     * performed exclusively on local data to ensure immediate user feedback and offline
     * functionality.
     *
     * **Side Effects:**
     * - Character becomes immediately available in getFavoriteCharacters() results
     * - UI elements can immediately reflect the favorite status change
     * - No impact on the character's other attributes (name, image, etc.)
     *
     * @param id CharacterId type-safe identifier of the character to mark as favorite.
     *           Must correspond to an existing character in the database.
     *
     */
    override suspend fun saveCharacterFavorite(id: CharacterId) {
        // Update the character's favorite status to true in the local database
        // This change is immediately persistent and will be reflected in all subsequent queries
        database.updateFavoriteStatus(id, true)
    }

    /**
     * Removes a character from favorites by updating its favorite status in local storage.
     *
     * This method provides the ability to remove characters from the user's favorites collection
     * by updating the character's favorite status in the local database. The operation is
     * performed exclusively on local data to ensure immediate user feedback and maintain
     * consistency with the offline-first approach used throughout the repository.
     *
     * @param id CharacterId type-safe identifier of the character to remove from favorites.
     *           Must correspond to an existing character in the database.
     *
     */
    override suspend fun removeCharacterFavorite(id: CharacterId) {
        // Update the character's favorite status to false in the local database
        // This change is immediately persistent and will remove the character from favorites
        database.updateFavoriteStatus(id, false)
    }
}
