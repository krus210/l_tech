package ru.korolevss.l_tech.repository

import retrofit2.Response
import retrofit2.http.*
import ru.korolevss.l_tech.model.AuthResult
import ru.korolevss.l_tech.model.Post
import ru.korolevss.l_tech.model.PhoneMask

interface APIMain {

    @GET("api/v1/phone_masks")
    suspend fun getPhoneMask(): Response<PhoneMask>

    @POST("api/v1/auth")
    suspend fun postAuth(
        @Query("phone") phone: String,
        @Query("password") password: String,
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded"
    ): Response<AuthResult>

}