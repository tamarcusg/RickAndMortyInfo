package com.example.rickandmortyinfo.repository

import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal interface CharacterRepository {
    suspend fun getCharacter(
        id: Int
    ): ApiResult<CharacterData>

    suspend fun getCharacters(
        searchString: String? = null
    ): ApiResult<PageData>

    suspend fun getPage(
        pageUrl: String
    ): ApiResult<PageData>
}

@Serializable
internal data class CharacterData(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val species: String,
    val status: String,
    val origin: String,
    val type: String?,
    val createdDate: String
)

fun String.toFormattedDate(): String {
    val instant = Instant.parse(this)
    val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    return dateTime.format(formatter)
}

internal data class PageData(
    val count: Int,
    val nextPage: String?,
    val characters: List<CharacterData>
)

internal sealed class ApiResult<out T: Any> {
    data class Success<out T: Any>(val data: T): ApiResult<T>()
    data object Error: ApiResult<Nothing>()
}