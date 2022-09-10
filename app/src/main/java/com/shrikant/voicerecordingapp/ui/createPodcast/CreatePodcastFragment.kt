package com.shrikant.voicerecordingapp.ui.createPodcast

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shrikant.voicerecordingapp.R
import com.shrikant.voicerecordingapp.data.model.RecordingModel
import com.shrikant.voicerecordingapp.databinding.FragmentCreatePodcastBinding
import java.io.File


class CreatePodcastFragment : Fragment() {

    private var _binding: FragmentCreatePodcastBinding? = null
    private val binding get() = _binding!!


    private lateinit var recordingAdapter: RecordingListAdapter

    lateinit var mediaPlayer: MediaPlayer
    private val createPodcastViewModel by activityViewModels<CreatePodcastViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCreatePodcastBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recordingList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recordingAdapter = RecordingListAdapter(::onRecordingClick)
        binding.recordingList.adapter = recordingAdapter
        createPodcastViewModel.getRecordingList()
        binding.buttonStartRecording.setOnClickListener {
            findNavController().navigate(R.id.action_createPodcastFragment_to_recordPodcastFragment)
        }


        binding.importFiles.setOnClickListener {
            val selectAudioIntent = Intent()
            selectAudioIntent.type = "audio/*"
            selectAudioIntent.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch("audio/*")

        }

        

        bindObserver()

    }


    var resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent(), ActivityResultCallback {
        val fileName: String = File(it.toString()).toURI().toString()
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(fileName)
        mediaPlayer.prepare()
        mediaPlayer.start()
    })


    fun bindObserver() {
        createPodcastViewModel.recordingList.observe(requireActivity(), Observer {
            if (it.size == 0) {
                binding.textNoData.visibility = View.VISIBLE
                binding.recordingList.visibility = View.GONE
            } else {
                binding.textNoData.visibility = View.GONE
                binding.recordingList.visibility = View.VISIBLE
                recordingAdapter.submitList(it)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        createPodcastViewModel.getRecordingList()
    }

    fun onRecordingClick(recording: RecordingModel, position: Int) {
        createPodcastViewModel.updateRecordingPlayStatus(recording)

        if (recording.isPlaying == true) {
            recording.isPlaying = false
            createPodcastViewModel.stopMedia()
        } else {
            recording.isPlaying = true
            createPodcastViewModel.playMedia(recording)
        }
    }


}