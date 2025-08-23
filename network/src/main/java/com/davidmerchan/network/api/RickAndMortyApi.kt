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

class RickAndMortyApi @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getAllCharacters(): CharactersResponseDto {
        return httpClient.get("character").body()
    }

    suspend fun getCharacter(id: Int): CharacterDto {
        return httpClient.get("character/$id").body()
    }
}

suspend inline fun <T> safeApiCall(crossinline call: suspend () -> T): Result<T> =
    try {
        Result.success(call())
    } catch (e: RedirectResponseException) {
        // Manage 3xx errors with expectSuccess=true
        Result.failure(e)
    } catch (e: ClientRequestException) {
        // Manage 4xx errors
        Result.failure(e)
    } catch (e: ServerResponseException) {
        // Manage 5xx errors
        Result.failure(e)
    } catch (e: Throwable) {
        Result.failure(e)
    }