package com.mahmoud.belal.postsapp.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mahmoud.belal.postsapp.application.ToastHelper
import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.domain.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(
    private val repository: PostRepository,
    private val toastHelper: ToastHelper,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val currentPostsListSize = savedStateHandle.getLiveData<String>("postNumbers", "11")

    fun setPost(postNumbers: String) {
        currentPostsListSize.value = postNumbers
    }

    val fetchPostsList = currentPostsListSize.distinctUntilChanged().switchMap { postsNumbers ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                repository.getPostsByPagingRemotely(itemNumbers = postsNumbers, postName = "")
                    .collect {
                        emit(it)
                    }

            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

}