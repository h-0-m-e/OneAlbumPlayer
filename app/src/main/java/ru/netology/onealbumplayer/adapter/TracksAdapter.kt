package ru.netology.onealbumplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.onealbumplayer.databinding.TrackItemBinding
import ru.netology.onealbumplayer.dto.Track
import ru.netology.onealbumplayer.listener.OnInteractionListener

class TracksAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Track, TrackViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return TrackViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        holder.bind(track)


    }
}

class TrackViewHolder(
    private val binding: TrackItemBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(track: Track) {
        binding.apply {
            title.text = track.file.removeSuffix(".mp3")
            playButton.isChecked = track.isPlaying


            playButton.setOnClickListener {
                onInteractionListener.onPlay(track)
            }
        }
    }

}

object DiffCallback : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem == newItem
}

