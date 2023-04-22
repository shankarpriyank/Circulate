package com.priyank.circulate.main.dao

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.priyank.circulate.authentication.model.UserInfo
import com.priyank.circulate.main.model.Post
import com.priyank.circulate.main.model.UploadStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val statusFlow = MutableStateFlow<UploadStatus>(UploadStatus.UpLoading())
    val database = Firebase.firestore
    val postCollection = database.collection("post")

    private fun addPost(image: String?, text: String, user: UserInfo) {

        val post = Post(createdBy = user, description = text, imageUrl = image)
        postCollection.add(post)
        GlobalScope.launch {

            statusFlow.emit(UploadStatus.Success())
        }
    }

    fun uploadPost(
        image: Uri?,
        text: String,
        user: UserInfo
    ): kotlinx.coroutines.flow.Flow<UploadStatus> {
        if (image != null) {

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
        } else {
            addPost(text = text, user = user, image = null)
            GlobalScope.launch {
                statusFlow.emit(UploadStatus.Success())
            }
        }

        return statusFlow
    }

    suspend fun getAllPosts(): List<Post> {

        val postList = postCollection.get().await().toObjects(Post::class.java)

        postCollection.addSnapshotListener { snapshot, error ->
            if (snapshot == null || error != null) {
                Log.e("FireStore ERR", "$error")
                return@addSnapshotListener
            } else {
                val postlist = snapshot.toObjects(Post::class.java)
                for (post in postlist) {
                    Log.i("Info", "$post")
                }
            }
        }

        return postList
    }
}
