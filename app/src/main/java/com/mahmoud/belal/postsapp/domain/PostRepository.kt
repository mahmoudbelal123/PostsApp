package com.mahmoud.belal.postsapp.domain

import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.data.model.PostEntity
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    suspend fun getPostsByPagingRemotely(
        itemNumbers: String,
        postName: String
    ): Flow<Resource<List<Post>>>

    suspend fun getCachedPostByNameLocal(postName: String): Resource<Post>
    suspend fun getCachedPostsByLimitLocal(postsNumbers: String): Resource<List<Post>>
    suspend fun savePostLocal(post: PostEntity)

}