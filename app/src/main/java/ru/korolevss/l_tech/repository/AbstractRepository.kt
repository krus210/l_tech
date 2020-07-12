package ru.korolevss.l_tech.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class AbstractRepository(
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
) {
    companion object {
        private const val BASE_URL = "http://dev-exam.l-tech.ru/"
    }
}