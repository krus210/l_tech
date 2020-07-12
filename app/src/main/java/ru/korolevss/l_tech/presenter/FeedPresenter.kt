package ru.korolevss.l_tech.presenter

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import ru.korolevss.l_tech.R
import ru.korolevss.l_tech.contract.FeedContract
import ru.korolevss.l_tech.exception.GetPostsException
import ru.korolevss.l_tech.model.Post
import ru.korolevss.l_tech.repository.FeedRepository
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FeedPresenter(
    private val feedView: FeedContract.View,
    private val feedRepository: FeedContract.Repository = FeedRepository
) : FeedContract.Presenter {

    companion object {
        private const val BASE_URL = "http://dev-exam.l-tech.ru/"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getListServerSort(): MutableList<Post> {
        try {
            val list = feedRepository
                .getPosts()
                .sortedBy { it.sort }.toMutableList()
            list.forEach {
                    it.image = BASE_URL + it.image
                    val date = getDateFromString(it.date)
                    it.date = setFormatDateString(date)
                }
            return list
        } catch (e: GetPostsException) {
            feedView.showText(R.string.loading_posts_failed)
            return mutableListOf()
        } catch (e: IOException) {
            feedView.showText(R.string.server_connection_fail)
            return mutableListOf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getListDateSort(): MutableList<Post> {
        try {
            val list = feedRepository
                .getPosts()
                .sortedByDescending {
                    getDateFromString(it.date)
                }.toMutableList()
            list.forEach {
                it.image = BASE_URL + it.image
                val date = getDateFromString(it.date)
                it.date = setFormatDateString(date)
            }
            return list
        } catch (e: GetPostsException) {
            feedView.showText(R.string.loading_posts_failed)
            return mutableListOf()
        } catch (e: IOException) {
            feedView.showText(R.string.server_connection_fail)
            return mutableListOf()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateFromString(dataString: String): ZonedDateTime = ZonedDateTime.parse(dataString)

    @RequiresApi(Build.VERSION_CODES.O)
    fun setFormatDateString(date: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm")
        return date.format(formatter)
    }

}