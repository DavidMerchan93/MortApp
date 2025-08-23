package com.davidmerchan.data.mapper

import com.davidmerchan.domain.entities.Location
import com.davidmerchan.network.dto.LocationDto

internal fun LocationDto.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        created = created,
        dimension = dimension,
        residents = residents,
        type = type,
        url = url
    )
}