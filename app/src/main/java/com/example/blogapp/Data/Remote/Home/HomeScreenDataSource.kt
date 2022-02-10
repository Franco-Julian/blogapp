package com.example.blogapp.Data.Remote.Home

import com.example.blogapp.Core.Result
import com.example.blogapp.Data.Model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPost(): Result<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }
        return Result.Success(postList)
    }
}