package ru.korolevss.l_tech

import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.launch
import ru.korolevss.l_tech.adapter.PostAdapter
import ru.korolevss.l_tech.adapter.PostDiffUtilCallback
import ru.korolevss.l_tech.contract.FeedContract
import ru.korolevss.l_tech.model.Post
import ru.korolevss.l_tech.presenter.FeedPresenter

class FeedFragment : Fragment(), FeedContract.View, PostAdapter.OnViewClickListener  {

    private val feedPresenter: FeedContract.Presenter by lazy {
        FeedPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onViewClicked(post: Post) {
        val postFragment = PostFragment.newInstance(post.image, post.title, post.text)
        val transaction = fragmentManager?.beginTransaction()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction?.replace(R.id.activity_container, postFragment)
            transaction?.addToBackStack(null)
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val oldFragment = fragmentManager?.findFragmentById(R.id.posts_container)
            if (oldFragment != null) {
                transaction?.remove(oldFragment)
            }
            transaction?.add(R.id.posts_container, postFragment)
        }
        transaction?.commit()
    }

    private suspend fun loadFirstTime() {
        determinateBarFeed.isVisible = true
        val list = feedPresenter.getListServerSort()
        with(container) {
            layoutManager = LinearLayoutManager(context)
            adapter = PostAdapter(list).apply {
                viewClickListener = this@FeedFragment
            }
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
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.app_name)
    }


}