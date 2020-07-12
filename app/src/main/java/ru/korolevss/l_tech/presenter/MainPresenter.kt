package ru.korolevss.l_tech.presenter

import android.util.Log
import ru.korolevss.l_tech.R
import ru.korolevss.l_tech.exception.GetPhoneMaskException
import ru.korolevss.l_tech.exception.GetResultAuthException
import ru.korolevss.l_tech.contract.MainContract
import ru.korolevss.l_tech.repository.MainRepository
import java.io.IOException

class MainPresenter(
    private val mainView: MainContract.View,
    private val mainRepository: MainContract.Repository = MainRepository
): MainContract.Presenter {

    override suspend fun onCreateAuth() {
        try {
            val phoneMask = mainRepository.getPhoneMask()
            mainView.setPhoneMask(phoneMask.phoneMask)
        } catch(e: GetPhoneMaskException)  {
            mainView.showText(R.string.phone_mask_fail)
        } catch (e: IOException) {
            mainView.showText(R.string.server_connection_fail)
            Log.d("IO_EXCEPTION", "$e")
        }
    }

    override suspend fun onButtonAuthClicked(phone: String, password: String) {
        if (phone.isEmpty() || password.isEmpty()) {
            mainView.showText(R.string.phone_password_empty)
        } else {
            try {
                val phoneFiltered = phone.filterNot { it == '-' || it == '+' || it == ' '}
                val authResult = mainRepository.postAuth(phoneFiltered, password)
                if (authResult.success) {
                    mainView.moveToFeed(phoneFiltered, password)
                } else mainView.showText(R.string.authentication_failed)
            } catch(e: GetResultAuthException)  {
                mainView.showText(R.string.phone_mask_fail)
            } catch (e: IOException) {
                mainView.showText(R.string.server_connection_fail)
            }
        }

    }
}