package com.example.blogapp.Domain.Camera

import android.graphics.Bitmap

interface CameraRepo {
    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String)
}