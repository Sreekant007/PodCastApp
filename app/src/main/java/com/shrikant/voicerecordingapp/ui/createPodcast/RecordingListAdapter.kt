package com.shrikant.voicerecordingapp.ui.createPodcast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shrikant.voicerecordingapp.R
import com.shrikant.voicerecordingapp.data.model.RecordingModel
import com.shrikant.voicerecordingapp.databinding.ItemRecordingListBinding

class RecordingListAdapter(private val onRecordingClick: (RecordingModel, position: Int) -> Unit) : ListAdapter<RecordingModel, RecordingListAdapter.RecordingViewHolder>(ComparatorDiffUtil()) {


    private var lastItemPlayed: Int = -1

    inner class RecordingViewHolder(val binding: ItemRecordingListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(recordingModel: RecordingModel, position: Int) {
            binding.recordingTitle.text = recordingModel.title
            binding.recordingDuration.text = recordingModel.recordingDuration

            binding.recordingDate.text = recordingModel.createdDate
            if (position == lastItemPlayed) {
                if (recordingModel.isPlaying) {
                    recordingModel.isPlaying = true
                }
            } else {
                recordingModel.isPlaying = false
            }

            if (recordingModel.isPlaying) {
                binding.playIcon.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            } else {
                binding.playIcon.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24)

            }

            binding.root.setOnClickListener {
                lastItemPlayed = position
                onRecordingClick(recordingModel, position)
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingListAdapter.RecordingViewHolder {
        val binding = ItemRecordingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordingListAdapter.RecordingViewHolder, position: Int) {
        val recording = getItem(position)
        recording?.let {
            holder.binding(recording, position)
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<RecordingModel>() {
        override fun areItemsTheSame(oldItem: RecordingModel, newItem: RecordingModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: RecordingModel, newItem: RecordingModel): Boolean {
            return oldItem == newItem
        }
    }
}