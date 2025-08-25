package com.davidmerchan.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity representing a Character in the local SQLite database.
 *
 * This data class defines the database schema for storing character information
 * from the Rick and Morty API. It serves as the local cache representation of
 * character data, enabling offline access and improved performance through
 * local storage.
 *
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "location_name")
    val locationName: String,
    @ColumnInfo(name = "location_url")
    val locationUrl: String,
    @ColumnInfo(name = "origin_name")
    val originName: String,
    @ColumnInfo(name = "origin_url")
    val originUrl: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)
