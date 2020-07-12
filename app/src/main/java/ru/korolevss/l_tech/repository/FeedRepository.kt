package ru.korolevss.l_tech.repository

import ru.korolevss.l_tech.contract.FeedContract
import ru.korolevss.l_tech.exception.GetPostsException
import ru.korolevss.l_tech.model.Post

object FeedRepository: AbstractRepository(), FeedContract.Repository {

    private val API_FEED: APIFeed = retrofit.create(APIFeed::class.java)

    override suspend fun getPosts(): MutableList<Post> {
        val result = API_FEED.getPosts()
        if (result.isSuccessful) {
            return result.body()!!
        } else throw GetPostsException()
    }
}