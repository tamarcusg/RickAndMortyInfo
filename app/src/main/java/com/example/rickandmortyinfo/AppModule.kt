package com.example.rickandmortyinfo

import com.example.rickandmortyinfo.details.CharacterDetailsScreenViewModel
import com.example.rickandmortyinfo.home.HomeScreenViewModel
import com.example.rickandmortyinfo.repository.ApiService
import com.example.rickandmortyinfo.repository.CharacterRepository
import com.example.rickandmortyinfo.repository.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    private const val CONTENT_TYPE = "application/json; charset=UTF8"

    @Singleton
    @Provides
    internal fun provideJsonSerializer(): Json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    internal fun provideRetrofit(
        json: Json
    ) = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    internal fun provideApiService(
        retrofit: Retrofit
    ) = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    internal fun provideCharacterRepository(
        apiService: ApiService
    ): CharacterRepository = CharacterRepositoryImpl(apiService)

    @Singleton
    @Provides
    internal fun provideHomeScreenViewModel(
        characterRepository: CharacterRepository
    ): HomeScreenViewModel = HomeScreenViewModel(characterRepository)

    @Singleton
    @Provides
    internal fun provideDetailScreenViewModel(
        characterRepository: CharacterRepository
    ): CharacterDetailsScreenViewModel = CharacterDetailsScreenViewModel(characterRepository)
}