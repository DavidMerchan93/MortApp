package com.davidmerchan.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * HTTP Client Factory for Rick and Morty API
 *
 * This object provides a pre-configured Ktor HTTP client specifically designed for interacting
 * with the Rick and Morty API. The client includes comprehensive configuration for content
 * negotiation, timeout management, error handling, and logging to ensure reliable and
 * maintainable network operations.
 *
 */
object RickAndMortyClient {
    /**
     * Base URL for the Rick and Morty API
     */
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    /**
     * Creates and configures a new HTTP client instance for Rick and Morty API
     *
     * @return HttpClient A configured HTTP client ready for API operations
     */
    fun create(): HttpClient =
        HttpClient(Android) {
            // Configure JSON content negotiation for automatic serialization/deserialization
            install(ContentNegotiation) {
                json(
                    Json {
                        // Automatically coerce invalid input values to default values instead of throwing exceptions
                        coerceInputValues = true
                        // Ignore unknown JSON properties to maintain compatibility with API changes
                        ignoreUnknownKeys = true
                        // Enable lenient parsing to handle malformed JSON gracefully
                        isLenient = true
                    },
                )
            }

            // Configure default request settings applied to all HTTP requests
            defaultRequest {
                // Set base URL so all requests can use relative paths
                url(BASE_URL)
                headers {
                    // Accept JSON responses from the server
                    append(HttpHeaders.Accept, ContentType.Application.Json)
                    // Declare JSON content type for request bodies
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }

            // Configure timeout strategies for different network scenarios
            install(HttpTimeout) {
                // Maximum time to wait for a complete request-response cycle (15 seconds)
                requestTimeoutMillis = 15_000
                // Maximum time to establish initial connection to server (10 seconds)
                connectTimeoutMillis = 10_000
                // Maximum time between consecutive data packets (15 seconds)
                socketTimeoutMillis = 15_000
            }

            // Configure comprehensive logging for debugging and monitoring
            install(Logging) {
                // Use Android-specific logger for proper logcat integration
                logger = Logger.ANDROID
                // Log complete request/response bodies for detailed debugging
                level = LogLevel.BODY
                // Protect sensitive headers (like Authorization) from being logged
                sanitizeHeader { it.equals(HttpHeaders.Authorization, ignoreCase = true) }
            }
        }
}
