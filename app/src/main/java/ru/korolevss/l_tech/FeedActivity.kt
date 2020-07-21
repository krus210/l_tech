package ru.korolevss.l_tech

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FeedActivity : AppCompatActivity() {

    private companion object {
        const val POST_ACTIVITY = 100
    }

    private val fragmentTransaction by lazy { supportFragmentManager.beginTransaction() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val fragmentFeed = FeedFragment()
        val oldFragment = supportFragmentManager.findFragmentById(R.id.activity_container)
        if (oldFragment == null) {
            fragmentTransaction.add(R.id.activity_container, fragmentFeed)
            fragmentTransaction.commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            requestCode == POST_ACTIVITY &&
            resultCode == Activity.RESULT_OK
        ) {
            val oldFragment = supportFragmentManager.findFragmentById(R.id.posts_container)
            if (oldFragment != null) {
                fragmentTransaction.detach(oldFragment)
                fragmentTransaction.commit()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
