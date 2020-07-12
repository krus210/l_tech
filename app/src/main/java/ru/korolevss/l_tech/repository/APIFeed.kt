package ru.korolevss.l_tech.repository

import retrofit2.Response
import retrofit2.http.GET
import ru.korolevss.l_tech.model.Post

interface APIFeed {

    @GET("api/v1/posts")
    suspend fun getPosts(): Response<MutableList<Post>>
}