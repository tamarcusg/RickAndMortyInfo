package com.example.rickandmortyinfo.repository.model

import com.example.rickandmortyinfo.repository.CharacterData
import com.example.rickandmortyinfo.repository.PageData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PageResult(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val characters: List<CharacterResult>
) {
    fun toPageData() = PageData(
        count = info.count,
        nextPage = info.nextPage,
        characters = characters.map {
            CharacterData(
                id = it.id,
                name = it.name.trim(),
                imageUrl = it.imageUrl,
                species = it.species,
                status = it.status,
                origin = it.origin.name,
                type = it.type,
                createdDate = it.createdDate
            )
        })
}

@Serializable
internal data class Info(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val nextPage: String?,
    @SerialName("prev")
    val previousPage: String?
)

@Serializable
internal data class CharacterResult(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val imageUrl: String,
    @SerialName("species")
    val species: String,
    @SerialName("status")
    val status: String,
    @SerialName("origin")
    val origin: CharacterOriginResult,
    @SerialName("type")
    val type: String?,
    @SerialName("created")
    val createdDate: String,
) {
    fun toCharacterData() = CharacterData(
        id = id,
        name = name,
        imageUrl = imageUrl,
        species = species,
        status = status,
        origin = origin.name,
        type = type,
        createdDate = createdDate
    )
}

@Serializable
internal data class CharacterOriginResult(
    @SerialName("name")
    val name: String
)