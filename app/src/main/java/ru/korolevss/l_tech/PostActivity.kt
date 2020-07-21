package ru.korolevss.l_tech

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val postFragment = PostFragment()
        fragmentTransaction.add(R.id.activity_post_container, postFragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        setResult(Activity.RESULT_OK)
        onBackPressed()
        finish()
        return true
    }

}