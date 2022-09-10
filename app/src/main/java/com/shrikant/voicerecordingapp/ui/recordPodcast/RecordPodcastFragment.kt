package com.shrikant.voicerecordingapp.ui.recordPodcast

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.shrikant.voicerecordingapp.R
import com.shrikant.voicerecordingapp.data.model.RecordingModel
import com.shrikant.voicerecordingapp.databinding.DialogSaveBinding
import com.shrikant.voicerecordingapp.databinding.FragmentRecordPodcastBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RecordPodcastFragment : Fragment() {

    private var _binding: FragmentRecordPodcastBinding? = null
    private val binding get() = _binding!!

    private val recordingViewModel by activityViewModels<RecordViewModel>()


    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        if (isGranted[RECORD_AUDIO] == true && isGranted[WRITE_EXTERNAL_STORAGE] == true) {
            recordingViewModel.startRecoding("hello")
            binding.recordingTimer.start()
        } else {
            binding.recordingTimer.stop()
            Snackbar.make(binding.root, "Permission Not Granted", Snackbar.LENGTH_SHORT).show()
        }
    }


    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordPodcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE))

        binding.saveRecording.setOnClickListener {
            saveDialog()
        }

        binding.buttonStopRecording.setOnClickListener {
            binding.recordingTimer.stop()
            recordingViewModel.stoprecording()
        }

        binding.btnUndo.setOnClickListener {
            recordingViewModel.startRecoding("hello")
            binding.recordingTimer.setBase(SystemClock.elapsedRealtime());
            binding.recordingTimer.start()
        }

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonPlay.setOnClickListener {
            if (recordingViewModel.isMediaPLaying.value == false) {
                recordingViewModel.playMedia("hello")
            } else {
                recordingViewModel.stopMedia()
            }
        }

        bindObserver()


    }

    private fun bindObserver() {
        recordingViewModel.isTimerStared.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.recordingView.visibility = View.VISIBLE
                binding.saveView.visibility = View.GONE
                binding.recordingTimer.start()
            } else {
                binding.recordingView.visibility = View.GONE
                binding.saveView.visibility = View.VISIBLE
                binding.recordingTimer.stop()
            }
        })


        recordingViewModel.isMediaPLaying.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.buttonPlay.setImageResource(R.drawable.ic_baseline_pause_24)
            } else {
                binding.buttonPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        })
    }

    fun saveDialog() {
        val dialog = BottomSheetDialog(requireActivity())
        var dialogbinding: DialogSaveBinding? = null
        dialogbinding = DialogSaveBinding.inflate(LayoutInflater.from(requireContext()))
        dialogbinding.btnSave.setOnClickListener {
            getFilePath(dialogbinding.edtSave.text.toString())
            val recording = RecordingModel(
                title = dialogbinding.edtSave.text.toString(),
                recordingDuration = binding.recordingTimer.text.toString(),

                createdDate = SimpleDateFormat("dd-MM hh:mm").format(Date()),
                isPlaying = false
            )
            recordingViewModel.insertRecording(recording)
            findNavController().popBackStack()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(dialogbinding.root)
        dialog.show()

    }

    private fun getFilePath(fileName: String): String {
        val contextWrapper = ContextWrapper(context)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val file = File(documentDirectory, "hello" + ".3gp")
        file.renameTo(File(documentDirectory, fileName + ".3gp"))
        return file.path
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onStop() {
        super.onStop()
        recordingViewModel.stopMedia()
    }
}