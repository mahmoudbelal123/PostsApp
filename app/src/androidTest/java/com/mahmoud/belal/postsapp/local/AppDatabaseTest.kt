package com.mahmoud.belal.postsapp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mahmoud.belal.postsapp.data.local.AppDatabase
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
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseNotOpen() {
        assertThat(database.isOpen).isFalse()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testIsDatabaseOpen() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.isOpen).isTrue()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabaseVersionIsCurrent() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.version).isEqualTo(1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testDatabasePathIsMemory() = runBlockingTest {
        executeDatabaseFunction()
        assertThat(database.openHelper.readableDatabase.path).isEqualTo(":memory:")
    }

    @After
    fun tearDown() {
        database.close()
    }

    @ExperimentalCoroutinesApi
    private fun executeDatabaseFunction() = runBlockingTest {
        val postEntity = PostEntity(
            0,
            "Mahmoud Belal",
            "https://cdn.pixabay.com/photo/2015/04/19/08/32/marguerite-729510__480.jpg"
        )
        database.postDao().savePost(postEntity)
    }
}