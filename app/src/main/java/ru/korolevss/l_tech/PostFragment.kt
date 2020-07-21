package ru.korolevss.l_tech

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_post.*


class PostFragment : Fragment() {

    private companion object {
        const val IMAGE = "IMAGE"
        const val TITLE = "TITLE"
        const val TEXT = "TEXT"
        private var image = ""
        private var title = ""
        private var detailedText = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getString(IMAGE) ?: ""
            title = it.getString(TITLE) ?: ""
            detailedText = it.getString(TEXT) ?: ""
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            image = activity?.intent?.getStringExtra(IMAGE) ?: ""
            title = activity?.intent?.getStringExtra(TITLE) ?: ""
            detailedText = activity?.intent?.getStringExtra(TEXT) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            activity?.title = title
        }

        textViewDetailedPostItem.text = detailedText
    }

}
