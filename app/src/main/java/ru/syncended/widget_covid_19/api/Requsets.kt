package ru.syncended.widget_covid_19.api

import retrofit2.http.GET
import ru.syncended.widget_covid_19.database.Count

interface Request {
    @GET("latestCounts.json")
    suspend fun getCount(): Array<Count>
}