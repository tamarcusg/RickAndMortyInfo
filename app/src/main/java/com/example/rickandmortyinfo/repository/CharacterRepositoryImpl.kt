package com.example.rickandmortyinfo.repository

import android.util.Log


internal class CharacterRepositoryImpl(
    private val apiService: ApiService
) : CharacterRepository {
    override suspend fun getCharacter(id: Int): ApiResult<CharacterData> {
        try {
            val result = apiService.getCharacter(id)
            return ApiResult.Success(result.toCharacterData())
        } catch (e: Exception) {
            return ApiResult.Error
        }
    }

    override suspend fun getCharacters(
        searchString: String?,
    ): ApiResult<PageData> {
        try {
            val result = apiService.getCharacters(searchString)
            return ApiResult.Success(result.toPageData())
        } catch (e: Exception) {
            Log.e("getCharacters Failed", "${e.message}")
            return ApiResult.Error
        }
    }

    override suspend fun getPage(pageUrl: String): ApiResult<PageData> {
        try {
            val result = apiService.getPage(pageUrl)
            return ApiResult.Success(result.toPageData())
        } catch (e: Exception) {
            Log.e("getPage Failed", "${e.message}")
            return ApiResult.Error
        }
    }

}