package com.example.blogapp.UI.Auth

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
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
import com.example.blogapp.Data.Remote.Home.Auth.AuthDataSource
import com.example.blogapp.Domain.Auth.AuthRepoImpl
import com.example.blogapp.Presentation.Auth.AuthViewModel
import com.example.blogapp.Presentation.Auth.AuthViewModelFactory
import com.example.blogapp.R
import com.example.blogapp.databinding.FragmentSetupProfileBinding

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var binding: FragmentSetupProfileBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profileImage.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException){
                Toast.makeText(requireContext(),"Not camera found",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading photo...").create()
            bitmap?.let {
                if (username.isNotEmpty()) {
                    viewModel.updateUserProfile(imageBitmap = it, username = username).observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is Result.Loading -> {
                                alertDialog.show()
                            }
                            is Result.Success -> {
                                alertDialog.dismiss()
                                findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                            }
                            is Result.Failure -> {
                                alertDialog.dismiss()
                            }
                        }
                    })
                }
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

}