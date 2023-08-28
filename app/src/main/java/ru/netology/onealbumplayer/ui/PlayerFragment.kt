package ru.netology.onealbumplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.onealbumplayer.adapter.TracksAdapter
import ru.netology.onealbumplayer.databinding.PlayerFragmentBinding
import ru.netology.onealbumplayer.dto.Track
import ru.netology.onealbumplayer.listener.OnInteractionListener
import ru.netology.onealbumplayer.viewmodel.TrackViewModel

class PlayerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PlayerFragmentBinding.inflate(inflater, container, false)

        val viewModel by viewModels<TrackViewModel>()

        lifecycle.addObserver(viewModel.mediaObserver)

        val adapter = TracksAdapter(object : OnInteractionListener {
            override fun onPlay(track: Track) {
                viewModel.play(track)
            }

        })

//        fun refresh(position: Int){
//            adapter.notifyItemChanged(position)
//        }

        binding.list.adapter = adapter

        viewModel.album.observe(viewLifecycleOwner) { album ->
            with(binding){
                albumTitle.text = album.title
                albumSubtitle.text = album.subtitle
                artistName.text = album.artist
                releaseDate.text = album.published
                genre.text = album.genre
            }
        }
        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            adapter.submitList(tracks)

        }

        binding.infoButton.setOnClickListener {
            binding.infoGroup.isVisible = !binding.infoGroup.isVisible
        }

        return binding.root
    }
}
