package ru.syncended.widget_covid_19


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.syncended.widget_covid_19.network.DataParser
import ru.syncended.widget_covid_19.storage.CovidDB
import ru.syncended.widget_covid_19.storage.Total
import ru.syncended.widget_covid_19.utils.TimeUtils.getDate

class Widget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        //Init base text's
        var total = ""
        var recovered = ""
        var dead = ""
        var today = context.getString(R.string.loading)

        val job = GlobalScope.launch(Dispatchers.IO) {
            try {
                val info = DataParser.getData()
                total = info.total
                recovered = info.recovered
                dead = info.dead
                //Init db
                val db = CovidDB.getInstance(context)
                val dao = db.totalDao()

                val currentDate = getDate(System.currentTimeMillis())
                //Insert current date total
                dao.insert(Total(currentDate, total.toInt()))

                //Calculate today amount
                val prevDay = getDate(System.currentTimeMillis() - 24 * 3600 * 1000)
                val prevTotal = dao.get(prevDay)
                if(prevTotal != null) {
                    today = (total.toInt() - prevTotal.total).toString()
                }
            } catch (ex: Exception) {
            }
        }

        job.invokeOnCompletion {
            if (total.isNotEmpty()) {
                val views = RemoteViews(context.packageName, R.layout.widget)
                views.setTextViewText(R.id.text_total, total)
                views.setTextViewText(R.id.text_recovered, recovered)
                views.setTextViewText(R.id.text_today, today)
                views.setTextViewText(R.id.text_death, dead)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}