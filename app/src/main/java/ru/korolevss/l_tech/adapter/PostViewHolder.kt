package ru.korolevss.l_tech.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_card.view.*
import ru.korolevss.l_tech.PostActivity
import ru.korolevss.l_tech.R
import ru.korolevss.l_tech.model.Post

class PostViewHolder(private val adapter: PostAdapter, private val view: View) :
    RecyclerView.ViewHolder(view) {

    private companion object {
        const val IMAGE ="IMAGE"
        const val TITLE ="TITLE"
        const val TEXT ="TEXT"
    }

    init {
        view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val post = adapter.list[adapterPosition]
                val intent = Intent(view.context, PostActivity::class.java)
                intent.putExtra(IMAGE, post.image)
                intent.putExtra(TITLE, post.title)
                intent.putExtra(TEXT, post.text)
                view.context.startActivity(intent)
            }
        }
    }

    fun bind(post: Post) {
        with (view) {
            textViewTitle.text = post.title
            textViewDetailed.text = post.text
            textViewDate.text = post.date

            Glide.with(this)
                .load(post.image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .override(192, 192)
                .centerCrop()
                .into(imageViewPost)
        }
    }
}
