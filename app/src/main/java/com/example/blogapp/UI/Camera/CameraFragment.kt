package com.example.blogapp.UI.Camera

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.blogapp.Core.Result
import com.example.blogapp.Data.Remote.Camera.CameraDataSource
import com.example.blogapp.Data.Remote.Home.HomeScreenDataSource
import com.example.blogapp.Domain.Camera.CameraRepoImplements
import com.example.blogapp.Domain.Home.HomeScreenRepoImplement
import com.example.blogapp.Presentation.Camera.CameraViewModel
import com.example.blogapp.Presentation.Camera.CameraViewModelFactory
import com.example.blogapp.Presentation.HomeScreenViewModel
import com.example.blogapp.Presentation.HomeScreenViewModelFactory
import com.example.blogapp.R
import com.example.blogapp.databinding.FragmentCameraBinding


class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE = 2
    private var bitmap: Bitmap? = null
    private lateinit var bindind: FragmentCameraBinding
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImplements(
                CameraDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindind = FragmentCameraBinding.bind(view)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Not camera app found", Toast.LENGTH_SHORT).show()
        }


        bindind.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.uploadPhoto(it, bindind.etxtDescription.text.toString().trim()).observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is Result.Loading -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Uploading photo...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Result.Success -> {
                                findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error ${result.exception}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            bindind.postImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}