package com.priyank.circulate.main.upload

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.priyank.circulate.authentication.data.UserDetails
import com.priyank.circulate.main.dao.PostDao
import com.priyank.circulate.main.model.UploadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val userInfo: UserDetails,
    private val postDao: PostDao
) : ViewModel() {

    private val user = userInfo.getUserObject()

    suspend fun upload(image: Uri, desc: String) {
        postDao.uploadPost(image, desc, user).onEach { result ->

            when (result) {

                is UploadStatus.UpLoading -> {
                }
                is UploadStatus.Success -> {
                }

                is UploadStatus.Error -> {
                }
            }
        }.collect()
    }
}
