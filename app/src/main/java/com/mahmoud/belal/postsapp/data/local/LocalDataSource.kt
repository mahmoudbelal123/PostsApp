package com.mahmoud.belal.postsapp.data.local

import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.data.model.PostEntity
import com.mahmoud.belal.postsapp.data.model.asPostEntity
import com.mahmoud.belal.postsapp.data.model.asPostsList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LocalDataSource @Inject constructor(private val postDao: PostDao) {

    suspend fun savePost(post: PostEntity) {
        postDao.savePost(post)
    }

    suspend fun getCachedPostsByLimit(limitSize: String): Resource<List<Post>> {
        return Resource.Success(postDao.getPostsByLimit(limitSize).asPostsList())
    }

    suspend fun getCachedPostsByName(postName: String): Resource<Post> {
        return Resource.Success(postDao.getPostsByName(postName).asPostEntity())
    }

}