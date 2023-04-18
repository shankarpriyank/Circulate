package com.priyank.circulate.main.dao

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.priyank.circulate.main.model.UploadStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class PostDao {

    private fun addPost(image: String, text: String, uid: String) {
        val database = Firebase.firestore
        val myRef = database.collection("post")
    }

    fun uploadImage(image: Uri, text: String, uid: String): kotlinx.coroutines.flow.Flow<UploadStatus> {
        return flow<UploadStatus> {
            emit(UploadStatus.UpLoading())
            val storageRef = Firebase.storage.reference
            val photoref = storageRef.child("images/${System.currentTimeMillis()}-photo.jpg")

            photoref.putFile(image).continueWithTask { photoUploadTask ->

                Log.d("Storing Photo", "${photoUploadTask.result?.bytesTransferred}")

                photoref.downloadUrl
            }
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        addPost(photoref.downloadUrl.toString(), text, uid)

                    } else {

                    }
                }
        }
    }
}
