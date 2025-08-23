package com.davidmerchan.network.api

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object RickAndMortyClient {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    fun create(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
                explicitNulls = false
                isLenient = true
            })
        }

        // Base URL + headers por defecto
        defaultRequest {
            url(BASE_URL)
            headers {
                append(HttpHeaders.Accept, ContentType.Application.Json)
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

        // Timeouts
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 10_000
            socketTimeoutMillis  = 15_000
        }

        // Logging
        install(Logging) {
            level = LogLevel.INFO // o ALL en debug
            logger = Logger.DEFAULT
        }
    }
}