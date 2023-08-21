package ru.netology.onealbumplayer.listener

import ru.netology.onealbumplayer.dto.Track

interface OnInteractionListener {
    fun onPlay(track: Track)
    fun onPause(track: Track)
    fun onNext(track: Track)
    fun onPrevious(track: Track)
}
