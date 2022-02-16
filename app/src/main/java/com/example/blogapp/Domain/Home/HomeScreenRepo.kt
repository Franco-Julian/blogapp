package com.example.blogapp.Domain.Home

import com.example.blogapp.Core.Result
import com.example.blogapp.Data.Model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Result<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)
}