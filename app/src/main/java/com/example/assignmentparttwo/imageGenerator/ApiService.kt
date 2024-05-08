package com.example.assignmentparttwo.imageGenerator

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.Result

private  val retrofit = Retrofit.Builder().baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dictionaryResponse = retrofit.create(ApiService::class.java)

interface ApiService{

    @GET("{word}")

    suspend fun getDefinition(@Path("word") defination: String) : Response<List<WordResult>>
}

//Image generator image service
private  val retrofitImage = Retrofit.Builder().baseUrl("https://api.unsplash.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val imageResponse = retrofitImage.create(ImageApiService::class.java)

interface ImageApiService{
    @Headers("Authorization: Client-ID Q8P_E1_LxbbWykHUqps84SdXvWy7cEykVdv-3r0f8_s")
    @GET("/search/photos")
    suspend fun searchImage(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String,
        @Query("query") word: String): ApiResponse
}