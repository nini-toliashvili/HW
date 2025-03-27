package com.example.homework.presentation.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.homework.databinding.FragmentMainBinding
import com.example.homework.presentation.BaseFragment
import com.example.homework.presentation.event.PhotoEvent
import com.example.homework.presentation.state.CameraState
import com.example.homework.presentation.state.UploadImageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val photoViewModel: PhotoViewModel by viewModels()
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                photoViewModel.state.value.let { state ->
                    if (state is CameraState.Captured) {
                        binding.photoPlaceholder.setImageURI(state.uri)
                    }
                }
            }
        }
    private var image : Uri? = null

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    photoViewModel.onEvent(PhotoEvent.ImageSelected(uri))
                }
            }
        }

    override fun setUp() {


        viewLifecycleOwner.lifecycleScope.launch {
            photoViewModel.uploadState.collect { state ->
                when (state) {
                    is UploadImageState.Idle -> {}
                    is UploadImageState.Uploading -> {
                        binding.photoPlaceholder.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is UploadImageState.Success -> {
                        binding.photoPlaceholder.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    is UploadImageState.Error -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            photoViewModel.state.collect { state ->
                when (state) {
                    is CameraState.Captured -> {
                        takePictureLauncher.launch(state.uri)
                        image = state.uri
                    }
                    is CameraState.Error -> Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    is CameraState.DisplayLast -> binding.photoPlaceholder.setImageURI(state.uri)
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            photoViewModel.galleryState.collect { state ->
                state.imageUri?.let {
                    binding.photoPlaceholder.setImageURI(it)
                    image = it
                }
                state.error?.let { }
            }

        }



        binding.apply {
            takePhotoBtn.setOnClickListener {
                onCameraButton()
            }

            chooseFromGalleryBtn.setOnClickListener {
                onChooseFromGalleryButton()
            }

            uploadBtn.setOnClickListener {
                image?.let { it1 -> photoViewModel.uploadImage(it1) }
            }
        }


    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE: Int = 1001
    }

    private fun onCameraButton() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            photoViewModel.onEvent(PhotoEvent.CaptureImage)
        }
    }

    private fun onChooseFromGalleryButton() {
        photoViewModel.onEvent(PhotoEvent.OpenGallery)
        getImageFromGallery.launch(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        )
    }


}