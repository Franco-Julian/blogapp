package com.example.blogapp.Domain.Camera

import android.graphics.Bitmap
import com.example.blogapp.Data.Remote.Camera.CameraDataSource

class CameraRepoImplements(private val dataSource: CameraDataSource): CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}