package com.mahmoud.belal.postsapp.application.injection

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.mahmoud.belal.postsapp.application.AppConstants.BASE_URL
import com.mahmoud.belal.postsapp.application.AppConstants.DATABASE_NAME
import com.mahmoud.belal.postsapp.data.local.AppDatabase
import com.mahmoud.belal.postsapp.data.remote.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePostDao(db: AppDatabase) = db.postDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)
}