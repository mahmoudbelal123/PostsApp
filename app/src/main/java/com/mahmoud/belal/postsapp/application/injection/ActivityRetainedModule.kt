package com.mahmoud.belal.postsapp.application.injection

import com.mahmoud.belal.postsapp.domain.DefaultPostRepository
import com.mahmoud.belal.postsapp.domain.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun dataSource(impl: DefaultPostRepository): PostRepository
}