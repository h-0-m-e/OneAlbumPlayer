package ru.netology.onealbumplayer.repository

import ru.netology.onealbumplayer.api.Api
import ru.netology.onealbumplayer.dto.Album

class TrackRepository {


    suspend fun getAlbum(): Album {
        var album = Album(
            0,"","","","","", emptyList()
        )
        try {
                val response = Api.service.getAlbum()
                if (!response.isSuccessful) {
                    throw Exception(response.message())
                } else {
                    album = (requireNotNull(response.body()))
                    println(album)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return album
    }

}
