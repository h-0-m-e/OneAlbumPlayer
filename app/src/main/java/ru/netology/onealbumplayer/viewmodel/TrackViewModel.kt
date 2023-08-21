package ru.netology.onealbumplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.onealbumplayer.dto.Album
import ru.netology.onealbumplayer.dto.Track
import ru.netology.onealbumplayer.observer.MediaLifecycleObserver
import ru.netology.onealbumplayer.repository.TrackRepository

private const val BASE_URL =
    "https://github.com/netology-code/andad-homeworks/raw/master/09_multimedia/data/"

class TrackViewModel : ViewModel() {

    val mediaObserver = MediaLifecycleObserver()

    private val repository = TrackRepository()

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>>
        get() = _tracks

    private var lastPlayedId: Long = 0

    init {
        getAlbum()
    }

    private fun getAlbum() = viewModelScope.launch {
        try {
            _album.value = repository.getAlbum()
            _tracks.value = _album.value?.tracks
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun play(track: Track) {
        _tracks.value =
            _tracks.value?.map { if (it.id == track.id) it.copy(isPlaying = true) else it }

        if (lastPlayedId != 0L) {
            _tracks.value =
                _tracks.value?.map { if (lastPlayedId == it.id) it.copy(isPlaying = false) else it }
        }

        mediaObserver.apply {
            //TODO Try to give a list to player
            if (lastPlayedId != 0L && lastPlayedId != track.id){
                mediaPlayer?.pause()
                mediaPlayer?.release()
            } else{
                lastPlayedId = track.id
            }

            mediaPlayer?.setDataSource(BASE_URL + _tracks.value!![(lastPlayedId - 1).toInt()].file)
            mediaObserver.mediaPlayer?.setOnCompletionListener {
                it.release()
                if (lastPlayedId > _tracks.value!!.size){
                    lastPlayedId = 1
                }else{
                    lastPlayedId++
                }
//                mediaPlayer?.setDataSource(BASE_URL + _tracks.value!![(lastPlayedId - 1).toInt()].file)
            }
//            mediaObserver.mediaPlayer?.setOnCompletionListener {
//                lastPlayedId = startTrackId
//                it.release()
//                    it.setDataSource(BASE_URL +
//                            if (track.id.toInt() > _tracks.value!!.size)
//                                _tracks.value!!.first().file .also { startTrackId = 1 }
//                            else
//                                _tracks.value!![(track.id - 1).toInt()]) .also { startTrackId++ }
//
//                }
//            }

        }.play()


    }

    fun pause(track: Track){
        mediaObserver.mediaPlayer?.pause()
    }
}
