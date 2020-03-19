package ru.syncended.widget_covid_19.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {

    private const val BASE_URL = "https://www.healthmap.org/covid-19/"

    fun retrofit(): Request = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Request::class.java)

}