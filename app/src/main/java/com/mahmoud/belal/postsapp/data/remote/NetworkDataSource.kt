package com.mahmoud.belal.postsapp.data.remote

import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.model.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


@ExperimentalCoroutinesApi
class NetworkDataSource @Inject constructor(
    private val webService: WebService
) {
    suspend fun getPostsByPaging(itemNumbers: String): Flow<Resource<List<Post>>> =
        callbackFlow {
            offer(
                Resource.Success(
                    webService.getPostsByPaging(itemNumbers)?.postList ?: listOf()
                )
            )
            awaitClose { close() }
        }
}