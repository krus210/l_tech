package ru.korolevss.l_tech.contract

import ru.korolevss.l_tech.model.AuthResult
import ru.korolevss.l_tech.model.PhoneMask

interface MainContract {
    interface View {
        suspend fun showText(message: Int)
        suspend fun setPhoneMask(phoneMaskText: String)
        suspend fun moveToFeed(phone: String, password: String)
    }

    interface Presenter {
        suspend fun onCreateAuth()
        suspend fun onButtonAuthClicked(phone: String, password: String)
    }

    interface Repository {
        suspend fun getPhoneMask(): PhoneMask
        suspend fun postAuth(phone: String, password: String): AuthResult
    }
}