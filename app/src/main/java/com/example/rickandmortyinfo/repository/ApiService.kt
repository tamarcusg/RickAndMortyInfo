package com.example.rickandmortyinfo.repository

import com.example.rickandmortyinfo.repository.model.CharacterResult
import com.example.rickandmortyinfo.repository.model.PageResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface ApiService {
    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterResult

    @GET("character")
    suspend fun getCharacters(
        @Query("name") name: String?
    ): PageResult

    @GET
    suspend fun getPage(
        @Url pageUrl: String
    ): PageResult
}