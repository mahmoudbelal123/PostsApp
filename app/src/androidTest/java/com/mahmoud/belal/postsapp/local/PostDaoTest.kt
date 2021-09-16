package com.mahmoud.belal.postsapp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mahmoud.belal.postsapp.data.local.AppDatabase
import com.mahmoud.belal.postsapp.data.local.PostDao
import com.mahmoud.belal.postsapp.data.model.PostEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: PostDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()
        dao = database.postDao()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testCachePostsLocal() = runBlockingTest {

        val postEntity = PostEntity(
            0,
            "Mahmoud Belal",
            "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"
        )
        dao.savePost(postEntity)
        val postsList = dao.getPostsByLimit("1") // at least 1
        assertThat(postsList).isNotEmpty()
        assertThat(postsList).contains(postEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetAllPosts() = runBlockingTest {

        val postEntityFirst = PostEntity(
            0,
            "Mahmoud Belal",
            "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"
        )
        val postEntitySecond = PostEntity(
            1,
            "Mahmoud Belal 2",
            "https://img.freepik.com/free-photo/copy-space-roses-flowers_23-2148860032.jpg?size=626&ext=jpg"
        )

        dao.savePost(postEntityFirst)
        dao.savePost(postEntitySecond)

        val postsList = dao.getPostsByLimit("2") //  size 2

        assertThat(postsList).isNotEmpty()
        assertThat(postsList.size).isEqualTo(2)
        assertThat(postsList).contains(postEntityFirst)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetPostsByName() = runBlockingTest {

        val postEntity = PostEntity(
            0,
            "Mahmoud Belal",
            "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"
        )
        dao.savePost(postEntity)

        val post = dao.getPostsByName(postEntity.postTitle)

        assertThat(post).isNotNull()
        assertThat(post).isEqualTo(postEntity)
    }

    @After
    fun tearDown() {
        database.close()
    }
}