package ru.netology.onealbumplayer.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.netology.onealbumplayer.dto.Album
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://github.com/netology-code/andad-homeworks/raw/master/09_multimedia/data/"

private val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService {

    @GET("album.json")
    suspend fun getAlbum(): Response<Album>

}

object Api {
    val service: ApiService by lazy { retrofit.create() }
}
