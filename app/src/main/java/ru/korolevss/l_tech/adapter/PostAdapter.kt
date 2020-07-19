package ru.korolevss.l_tech.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.korolevss.l_tech.R
import ru.korolevss.l_tech.model.Post

class PostAdapter(val list: MutableList<Post>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var viewClickListener: OnViewClickListener? = null

    interface OnViewClickListener {
        fun onViewClicked(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.post_card, parent, false)
        return PostViewHolder(this, view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = list[position]
        with(holder as PostViewHolder) {
            bind(post)
        }
    }

    fun updatePosts(newData: MutableList<Post>) {
        this.list.clear()
        this.list.addAll(newData)
    }
}

