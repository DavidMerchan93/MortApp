package com.davidmerchan.data.mapper

import com.davidmerchan.network.dto.CharacterDto
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.LocationCharacter
import com.davidmerchan.network.dto.LocationCharacterDto

internal fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        created = created,
        episode = episode.map { it },
        gender = gender,
        location = location.toDomain(),
        origin = origin.toDomain(),
        species = species,
        status = status,
        type = type,
        url = url
    )
}

internal fun LocationCharacterDto.toDomain(): LocationCharacter {
    return LocationCharacter(
        name = name,
        url = url
    )
}