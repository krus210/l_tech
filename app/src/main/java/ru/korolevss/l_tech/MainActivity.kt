package ru.korolevss.l_tech

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.korolevss.l_tech.contract.MainContract
import ru.korolevss.l_tech.presenter.MainPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class MainActivity : AppCompatActivity(), MainContract.View {

    private val mainPresenter: MainContract.Presenter by lazy {
        MainPresenter(this)
    }
    companion object {
        private const val SHARED_PREF_KEY = "SHARED_PREF"
        private const val PASSWORD = "PASSWORD"
        private const val PHONE = "PHONE"
        private const val MASK = "MASK"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phone = getAuthData().first
        val password = getAuthData().second
        if (phone.isNotEmpty() && password.isNotEmpty()) {
            val phoneMask = getMask()
            if (phoneMask.isNotEmpty()) {
                setMask(phoneMask)
                editTextPhone.setText(phone)
                editTextPassword.setText(password)
            }
        } else {
            lifecycleScope.launch {
                buttonAuth.isEnabled = false
                determinateBarMain.isVisible = true
                mainPresenter.onCreateAuth()
                determinateBarMain.isVisible = false
                buttonAuth.isEnabled = true
            }
        }

        buttonAuth.setOnClickListener {
            lifecycleScope.launch {
                determinateBarMain.isVisible = true
                val phoneAuth = editTextPhone.text.toString()
                val passwordAuth = editTextPassword.text.toString()
                mainPresenter.onButtonAuthClicked(phoneAuth, passwordAuth)
                determinateBarMain.isVisible = false
            }
        }
    }

    override suspend fun showText(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

    override suspend fun setPhoneMask(phoneMaskText: String) {
        val replacedPhoneMask = phoneMaskText.replace("Х", "_")
        setMask(replacedPhoneMask)
        saveMask(replacedPhoneMask)
    }

    private fun setMask(phoneMask: String) {
        val slots = UnderscoreDigitSlotsParser()
            .parseSlots(phoneMask)
        val formatWatcher = MaskFormatWatcher(
            MaskImpl.createTerminated(slots)
        )
        formatWatcher.installOn(editTextPhone)
    }

    override suspend fun moveToFeed(phone: String, password: String) {
        saveAuthData(phone, password)
        startActivity(Intent(this, FeedActivity::class.java))
        finish()
    }

    private fun saveAuthData(phone: String, password: String) {
        val sharedPref = getSharedPreferences(
            SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )
        sharedPref.edit {
            putString(PASSWORD, password)
            putString(PHONE, phone)
        }
    }

    private fun getAuthData(): Pair<String, String> {
        val phone = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .getString(PHONE, "") ?: ""
        val password = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .getString(PASSWORD, "") ?: ""
        return Pair(phone, password)
    }

    private fun saveMask(mask: String) {
        val sharedPref = getSharedPreferences(
            SHARED_PREF_KEY,
            Context.MODE_PRIVATE
        )
        sharedPref.edit {
            putString(MASK, mask)
        }
    }

    private fun getMask() = getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
            .getString(MASK, "") ?: ""
}