package ru.syncended.widget_covid_19.api

import retrofit2.http.GET
import retrofit2.http.Query

interface Request {
    @GET("latestCounts.json")
    suspend fun getCount(): Array<CountResponse>
}