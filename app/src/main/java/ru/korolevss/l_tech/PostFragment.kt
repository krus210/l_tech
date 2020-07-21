package ru.korolevss.l_tech

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_post.*

private const val IMAGE = "IMAGE"
private const val TITLE = "TITLE"
private const val TEXT = "TEXT"

class PostFragment : Fragment() {

    private var image: String? = null
    private var title: String? = null
    private var detailedText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = arguments?.getString(IMAGE) ?: ""
        title = arguments?.getString(TITLE) ?: ""
        detailedText = arguments?.getString(TEXT) ?: ""
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

        if (!image.isNullOrEmpty()) {
            Glide.with(this)
                .load(image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .override(400, 400)
                .centerCrop()
                .into(imageViewPostItem)
        }

        if (!title.isNullOrEmpty()) {
            textViewTitlePostItem.text = title
            activity?.title = title
        }

        textViewDetailedPostItem.text = detailedText ?: ""
    }

    companion object {
        @JvmStatic
        fun newInstance(image: String, title: String, detailedText: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE, image)
                    putString(TITLE, title)
                    putString(TEXT, detailedText)
                }
            }
    }

}
