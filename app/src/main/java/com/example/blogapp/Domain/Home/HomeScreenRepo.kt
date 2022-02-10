package com.example.blogapp.Domain.Home

import com.example.blogapp.Core.Result
import com.example.blogapp.Data.Model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>
}