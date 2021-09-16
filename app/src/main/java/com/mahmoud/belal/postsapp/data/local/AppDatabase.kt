package com.mahmoud.belal.postsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahmoud.belal.postsapp.data.model.PostEntity


@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}