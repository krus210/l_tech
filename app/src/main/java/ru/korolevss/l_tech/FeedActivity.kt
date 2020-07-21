package ru.korolevss.l_tech

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class FeedActivity : AppCompatActivity() {

    companion object {
        const val IMAGE = "IMAGE"
        const val TITLE = "TITLE"
        const val TEXT = "TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentFeed = FeedFragment()
        val oldFragment = supportFragmentManager.findFragmentById(R.id.activity_container)
        if (oldFragment == null) {
            fragmentTransaction.add(R.id.activity_container, fragmentFeed)
            fragmentTransaction.commit()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun recreateFragment(fragment: Fragment): Fragment {
        val image = fragment.arguments?.getString(IMAGE) ?: ""
        val title = fragment.arguments?.getString(TITLE) ?: ""
        val text = fragment.arguments?.getString(TEXT) ?: ""
        return PostFragment.newInstance(image, title, text)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(R.layout.activity_feed)
        val oldFragment = supportFragmentManager.findFragmentById(R.id.activity_container)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(oldFragment!!)
        if (oldFragment is PostFragment) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                val newPostFragment = recreateFragment(oldFragment)
                fragmentTransaction.add(R.id.posts_container, newPostFragment)
                fragmentTransaction.replace(R.id.activity_container, FeedFragment())
                fragmentTransaction.commit()
                supportFragmentManager.popBackStack()
            }
        } else if (oldFragment is FeedFragment) {
            fragmentTransaction.remove(oldFragment)
            fragmentTransaction.replace(R.id.activity_container, FeedFragment())
            fragmentTransaction.commit()
        }

    }


}
