package com.rankor.breakingbadapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BBApi {

    @GET("characters")
    suspend fun getBBList(): List<BBCharacterResponse>

    @GET("characters/{id}")
    suspend fun getBBCharacter(@Path("id") id: Int): List<BBCharacterResponse>

}