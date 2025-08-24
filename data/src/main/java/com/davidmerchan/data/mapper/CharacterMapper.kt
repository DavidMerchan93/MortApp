package com.davidmerchan.data.mapper

import com.davidmerchan.database.entities.CharacterEntity
import com.davidmerchan.domain.entities.Character
import com.davidmerchan.domain.entities.LocationCharacter
import com.davidmerchan.network.dto.CharacterDto
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
        url = url,
    )
}

internal fun LocationCharacterDto.toDomain(): LocationCharacter {
    return LocationCharacter(
        name = name,
        url = url,
    )
}

internal fun CharacterDto.toDatabaseEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        image = image,
        created = created,
        episode = episode.joinToString(),
        gender = gender,
        locationName = location.name,
        locationUrl = location.url,
        originName = origin.name,
        originUrl = origin.url,
        species = species,
        status = status,
        type = type,
        url = url,
    )
}

internal fun CharacterEntity.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        image = image,
        created = created,
        episode = episode.split(","),
        gender = gender,
        location =
            LocationCharacter(
                name = locationName,
                url = locationUrl,
            ),
        origin =
            LocationCharacter(
                name = originName,
                url = originUrl,
            ),
        species = species,
        status = status,
        type = type,
        url = url,
        isFavorite = isFavorite,
    )
}
