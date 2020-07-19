package ru.korolevss.l_tech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentFeed = FeedFragment()
        val oldFragment = fragmentManager.findFragmentById(R.id.activity_container)
        if (oldFragment == null) {
            fragmentTransaction.add(R.id.activity_container, fragmentFeed)
            fragmentTransaction.commit()
        }


    }

}
