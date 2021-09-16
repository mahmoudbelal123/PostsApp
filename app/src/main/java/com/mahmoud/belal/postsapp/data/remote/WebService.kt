package com.mahmoud.belal.postsapp.data.remote

import com.mahmoud.belal.postsapp.data.model.PostsList
import retrofit2.http.GET
import retrofit2.http.Query


interface WebService {
    @GET("posts")
    suspend fun getPostsByPaging(@Query(value = "quantity") itemNumbers: String): PostsList?
}