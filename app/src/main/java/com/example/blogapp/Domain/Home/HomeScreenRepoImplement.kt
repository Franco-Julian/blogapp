package com.example.blogapp.Domain.Home

import com.example.blogapp.Core.Result
import com.example.blogapp.Data.Model.Post
import com.example.blogapp.Data.Remote.Home.HomeScreenDataSource

class HomeScreenRepoImplement(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

    override suspend fun getLatestPosts(): Result<List<Post>> = dataSource.getLatestPosts()

    override suspend fun registerLikeButtonState(postId: String, liked: Boolean) =
        dataSource.registerLikeButtonState(postId, liked)
}