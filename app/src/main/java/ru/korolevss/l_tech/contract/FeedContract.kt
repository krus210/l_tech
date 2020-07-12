package ru.korolevss.l_tech.contract

import ru.korolevss.l_tech.model.Post

interface FeedContract {
    interface View {
        suspend fun showText(message: Int)
    }

    interface Presenter {
        suspend fun getListServerSort(): MutableList<Post>
        suspend fun getListDateSort(): MutableList<Post>
    }

    interface Repository {
        suspend fun getPosts(): MutableList<Post>
    }
}