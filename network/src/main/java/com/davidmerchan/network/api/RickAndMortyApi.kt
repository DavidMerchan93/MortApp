package com.davidmerchan.network.api

import com.davidmerchan.network.dto.CharacterDto
import com.davidmerchan.network.dto.CharactersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Rick and Morty API Service
 *
 * This service class provides a clean, type-safe interface for accessing the Rick and Morty API
 * endpoints. It encapsulates HTTP operations and response parsing, offering a high-level API
 * for retrieving character data from the Rick and Morty universe.
 *
 */
class RickAndMortyApi
@Inject
constructor(
    private val httpClient: HttpClient,
) {
    /**
     * Retrieves all characters from the Rick and Morty API
     *
     * This method fetches the first page of characters from the API, including pagination
     * information that can be used to retrieve subsequent pages. The response contains
     * both character data and metadata about the total number of characters and pages.
     *
     * The API returns a paginated response with up to 20 characters per page by default.
     * Use the pagination info in the response to determine if additional pages are available.
     *
     * HTTP Operation: GET /character
     *
     * @return CharactersResponseDto Complete response containing character list and pagination info
     * @throws ClientRequestException For 4xx HTTP status codes (client errors)
     * @throws ServerResponseException For 5xx HTTP status codes (server errors)
     * @throws RedirectResponseException For 3xx HTTP status codes (redirects)
     *
     */
    suspend fun getAllCharacters(): CharactersResponseDto {
        return httpClient.get("character").body()
    }

    /**
     * Retrieves a specific character by their unique identifier
     *
     * This method fetches detailed information about a single character using their
     * unique ID from the Rick and Morty API. The character ID should be a positive
     * integer corresponding to an existing character in the database.
     *
     * HTTP Operation: GET /character/{id}
     *
     * @param id The unique identifier of the character to retrieve (must be positive integer)
     * @return CharacterDto Complete character information including personal details, location, and episodes
     * @throws ClientRequestException For 4xx HTTP status codes (e.g., 404 if character not found)
     * @throws ServerResponseException For 5xx HTTP status codes (server errors)
     * @throws RedirectResponseException For 3xx HTTP status codes (redirects)
     *
     */
    suspend fun getCharacter(id: Int): CharacterDto {
        return httpClient.get("character/$id").body()
    }
}

/**
 * Safe API Call Wrapper
 *
 * This utility function provides comprehensive error handling for HTTP API operations by wrapping
 * them in a Result type. It catches all types of HTTP exceptions and general throwables,
 * converting them into a Result.failure() with the original exception preserved.
 *
 * @param T The expected return type of the API call
 * @param call The suspending API operation to execute safely
 * @return Result<T> Success with data or failure with the original exception
 *
 * @see Result for Kotlin's built-in result handling
 */
suspend inline fun <T> safeApiCall(crossinline call: suspend () -> T): Result<T> =
    try {
        Result.success(call())
    } catch (e: RedirectResponseException) {
        // Handle 3xx HTTP status codes (redirects)
        // Usually indicates API endpoint changes or configuration issues
        Result.failure(e)
    } catch (e: ClientRequestException) {
        // Handle 4xx HTTP status codes (client errors)
        // Common causes: invalid parameters, authentication issues, resource not found
        Result.failure(e)
    } catch (e: ServerResponseException) {
        // Handle 5xx HTTP status codes (server errors)
        // Indicates problems on the server side or temporary service unavailability
        Result.failure(e)
    } catch (e: Throwable) {
        // Handle any other exceptions (network issues, serialization errors, etc.)
        Result.failure(e)
    }
