package com.priyank.circulate.main.dao

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.priyank.circulate.authentication.model.UserInfo
import com.priyank.circulate.main.model.Post
import com.priyank.circulate.main.model.UploadStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val statusFlow = MutableStateFlow<UploadStatus>(UploadStatus.UpLoading())

    private fun addPost(image: String, text: String, user: UserInfo) {
        val database = Firebase.firestore
        val myRef = database.collection("post")

        val post = Post(user, text, image, null)
        myRef.add(post)
        GlobalScope.launch {


            statusFlow.emit(UploadStatus.Success())
        }

    }

    fun uploadImage(
        image: Uri,
        text: String,
        user: UserInfo
    ): kotlinx.coroutines.flow.Flow<UploadStatus> {

        val storageRef = Firebase.storage.reference
        val photoref = storageRef.child("images/${System.currentTimeMillis()}-photo.jpg")

        photoref.putFile(image).continueWithTask { photoUploadTask ->

            Log.d("Storing Photo", "${photoUploadTask.result?.bytesTransferred}")

            photoref.downloadUrl
        }
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    GlobalScope.launch {
                        addPost(photoref.downloadUrl.await().toString(), text, user)
                        statusFlow.emit(UploadStatus.Success())

                    }



                } else {
                    GlobalScope.launch {
                        statusFlow.emit(UploadStatus.Error())
                    }

                }
            }
        return statusFlow

    }
}
