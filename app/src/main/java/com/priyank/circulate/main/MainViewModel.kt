package com.priyank.circulate.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.main.dao.PostDao
import com.priyank.circulate.main.model.Post
import com.priyank.circulate.main.model.UploadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userInfo: UserDetails,
    private val postDao: PostDao
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _feed = MutableStateFlow(listOf<Post>())
    val feed = _feed
    private val user = userInfo.getUserObject()

    init {
        getAllPosts()
    }

    suspend fun upload(image: Uri?, desc: String?) {

        if (desc == "" || desc == null) {
            _eventFlow.emit(UIEvent.ShowToast("Description Can't Be Empty"))
        } else {
            _eventFlow.emit(UIEvent.ShowNotification("Upload Started"))

            postDao.uploadPost(image, desc, user).onEach { result ->

                when (result) {

                    is UploadStatus.UpLoading -> {
                    }
                    is UploadStatus.Success -> {
                        delay(3000)
                        _eventFlow.emit(UIEvent.ShowNotification("Upload Complete"))
                    }

                    is UploadStatus.Error -> {
                        _eventFlow.emit(UIEvent.ShowNotification("Upload Failed"))
                    }
                }
            }.collect()
        }
    }

    fun getAllPosts() {
        var posts = listOf<Post>()
        GlobalScope.launch(Dispatchers.IO) {
            posts = postDao.getAllPosts()
            _feed.emit(posts)
        }
    }

    sealed class UIEvent {
        data class ShowToast(val message: String) : UIEvent()
        data class ShowNotification(val message: String) : UIEvent()
    }
}
