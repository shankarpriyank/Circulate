package com.priyank.circulate.models

import com.priyank.circulate.authentication.model.UserInfo
import com.priyank.circulate.main.model.Comment
import com.priyank.circulate.main.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class PostTest {

    @Test
    fun createPostWithNonNullValues() {
        val createdBy = UserInfo("John Doe", "johndoe@example.com", "https://example.com/profile.jpg")
        val description = "This is a post"
        val imageUrl = "https://example.com/image.jpg"
        val timeOfPost = System.currentTimeMillis()
        val comments = listOf(
            Comment(UserInfo("Jane Smith", "janesmith@example.com", "https://example.com/profile.jpg"), "Great post!"),
            Comment(UserInfo("Bob Johnson", "bobjohnson@example.com", "https://example.com/profile.jpg"), "Nice work!")
        )
        val upVotes = 10L

        val post = Post(createdBy, description, imageUrl, timeOfPost, comments, upVotes)

        assertEquals(createdBy, post.createdBy)
        assertEquals(description, post.description)
        assertEquals(imageUrl, post.imageUrl)
        assertEquals(timeOfPost, post.timeOfPost)
        assertEquals(comments, post.comments)
        assertEquals(upVotes, post.upVotes)
    }

    @Test
    fun createPostWithNullValues() {
        val post = Post()

        assertEquals(null, post.createdBy)
        assertEquals(null, post.description)
        assertEquals(null, post.imageUrl)
        assertEquals(null, post.timeOfPost)
        assertEquals(null, post.comments)
        assertEquals(null, post.upVotes)
    }
}
