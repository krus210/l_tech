package ru.korolevss.l_tech.repository

import ru.korolevss.l_tech.exception.GetPhoneMaskException
import ru.korolevss.l_tech.exception.GetResultAuthException
import ru.korolevss.l_tech.contract.MainContract
import ru.korolevss.l_tech.model.AuthResult
import ru.korolevss.l_tech.model.PhoneMask



object MainRepository : AbstractRepository(), MainContract.Repository {

    private val API_MAIN: APIMain = retrofit.create(APIMain::class.java)

    override suspend fun getPhoneMask(): PhoneMask {
        val result = API_MAIN.getPhoneMask()
        if (result.isSuccessful) {
            return result.body()!!
        } else throw GetPhoneMaskException()
    }

    override suspend fun postAuth(phone: String, password: String): AuthResult {
        val result = API_MAIN.postAuth(phone, password)
        if (result.isSuccessful) {
            return result.body()!!
        } else throw GetResultAuthException()
    }

}