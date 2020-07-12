package ru.korolevss.l_tech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    private companion object {
        const val IMAGE ="IMAGE"
        const val TITLE ="TITLE"
        const val TEXT ="TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val image = intent.getStringExtra(IMAGE) ?: ""
        val title = intent.getStringExtra(TITLE) ?: ""
        val detailedText = intent.getStringExtra(TEXT) ?: ""

        if (image.isNotEmpty()) {
            Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .override(400, 400)
                .centerCrop()
                .into(imageViewPostItem)
        }
        if (title.isNotEmpty()) {
            textViewTitlePostItem.text = title
            supportActionBar?.title = title
        }

        textViewDetailedPostItem.text = detailedText

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }
}