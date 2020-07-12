package ru.korolevss.l_tech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.launch
import ru.korolevss.l_tech.adapter.PostAdapter
import ru.korolevss.l_tech.adapter.PostDiffUtilCallback
import ru.korolevss.l_tech.contract.FeedContract
import ru.korolevss.l_tech.model.Post
import ru.korolevss.l_tech.presenter.FeedPresenter

class FeedActivity : AppCompatActivity(), FeedContract.View {

    private val feedPresenter: FeedContract.Presenter by lazy {
        FeedPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        buttonServerSort.isChecked = true
        lifecycleScope.launch {
            loadFirstTime()
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.buttonServerSort -> {
                    lifecycleScope.launch {
                        updatePostsWithServerSort()
                    }
                }
                R.id.buttonByDateSort -> {
                    lifecycleScope.launch {
                        updatePostsWithByDateSort()
                    }
                }
            }
        }
        swipeContainer.setOnRefreshListener {
            lifecycleScope.launch {
                updatePostsWithRefresh()
            }
        }

    }

    private suspend fun loadFirstTime() {
        determinateBarFeed.isVisible = true
        val list = feedPresenter.getListServerSort()
        with(container) {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            adapter = PostAdapter(list)
        }
        determinateBarFeed.isVisible = false

    }

    private suspend fun updatePostsWithServerSort() {
        determinateBarFeed.isVisible = true
        val newList = feedPresenter.getListServerSort()
        updateRecyclerView(newList)
        determinateBarFeed.isVisible = false

    }

    private suspend fun updatePostsWithByDateSort() {
        determinateBarFeed.isVisible = true
        val newList = feedPresenter.getListDateSort()
        updateRecyclerView(newList)
        determinateBarFeed.isVisible = false

    }

    private fun updateRecyclerView(newList: MutableList<Post>) {
        with(container.adapter as PostAdapter) {
            val postDiffUtilCallback = PostDiffUtilCallback(list, newList)
            val postDiffResult = DiffUtil.calculateDiff(postDiffUtilCallback)
            updatePosts(newList)
            postDiffResult.dispatchUpdatesTo(this)
        }
    }

    private suspend fun updatePostsWithRefresh() {
        when (radioGroup.checkedRadioButtonId) {
            R.id.buttonServerSort -> {
                updatePostsWithServerSort()
            }
            R.id.buttonByDateSort -> {
                updatePostsWithByDateSort()
            }
        }
        swipeContainer.isRefreshing = false
    }

    override suspend fun showText(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }
}
