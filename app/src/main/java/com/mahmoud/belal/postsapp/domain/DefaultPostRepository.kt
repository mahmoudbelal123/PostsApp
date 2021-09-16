package com.mahmoud.belal.postsapp.domain

import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.local.LocalDataSource
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.data.model.PostEntity
import com.mahmoud.belal.postsapp.data.model.asPostEntity
import com.mahmoud.belal.postsapp.data.remote.NetworkDataSource

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class DefaultPostRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : PostRepository {

    override suspend fun getPostsByPagingRemotely(
        itemNumbers: String,
        postName: String
    ): Flow<Resource<List<Post>>> =
        callbackFlow {


            offer(getCachedPostsByLimitLocal(itemNumbers))

            networkDataSource.getPostsByPaging(itemNumbers).collect {
                when (it) {
                    is Resource.Success -> {
                        offer(it)
                        for (postName in it.data) {
                            savePostLocal(postName.asPostEntity())
                        }
                    }
                    is Resource.Failure -> {
                        offer(getCachedPostsByLimitLocal(itemNumbers))
                    }
                    Resource.Loading -> TODO()
                }
            }

            awaitClose { cancel() }
        }


    override suspend fun savePostLocal(post: PostEntity) {
        localDataSource.savePost(post)
    }

    override suspend fun getCachedPostByNameLocal(postName: String): Resource<Post> {
        return localDataSource.getCachedPostsByName(postName)
    }

    override suspend fun getCachedPostsByLimitLocal(postsNumbers: String): Resource<List<Post>> {
        return localDataSource.getCachedPostsByLimit(postsNumbers)
    }
}