package ru.syncended.widget_covid_19.network

import android.util.Log
import org.jsoup.Jsoup

object DataParser {
    private const val URL = "https://www.trackcorona.live/"

    fun getData(): InfectionStatistics {
        val doc = Jsoup.connect(URL).get()
        val total = doc.getElementById("valueTot")
        val recovered = doc.getElementById("valueRec")
        val dead = doc.getElementById("valueDed")
        return InfectionStatistics(total.text(), recovered.text(), dead.text())
    }
}