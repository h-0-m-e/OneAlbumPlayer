package ru.netology.onealbumplayer.dto

data class Track(
    val id: Long = 0,
    val file: String,
    val isPlaying: Boolean
)
