package com.mahmoud.belal.postsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoud.belal.postsapp.data.model.PostEntity

@Dao
interface PostDao {


    @Query("SELECT * FROM postTable LIMIT :limitSize")
    suspend fun getPostsByLimit(limitSize: String): List<PostEntity>

    @Query("SELECT * FROM postTable WHERE title LIKE '%' || :postTitle || '%'") // This Like operator is needed due that the API returns blank spaces in the name
    suspend fun getPostsByName(postTitle: String): PostEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePost(post: PostEntity)

}