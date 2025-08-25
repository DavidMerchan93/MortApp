package com.davidmerchan.network.dto

import kotlinx.serialization.Serializable

/**
 * Characters Response Data Transfer Object
 */
@Serializable
data class CharactersResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>,
)

/**
 * Pagination Information Data Transfer Object
 */
@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
