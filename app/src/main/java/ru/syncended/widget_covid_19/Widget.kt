package ru.syncended.widget_covid_19


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.syncended.widget_covid_19.network.DataParser

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
        var total = ""
        var recovered = ""
        var dead = ""
        val job = GlobalScope.launch(Dispatchers.IO) {
            val info = DataParser.getData()
            total = info.total
            recovered = info.recovered
            dead = info.dead
        }

        job.invokeOnCompletion {
            if (it != null) {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            } else {
                val views = RemoteViews(context.packageName, R.layout.widget)
                views.setTextViewText(R.id.text_total, total)
                views.setTextViewText(R.id.text_recovered, recovered)
                views.setTextViewText(R.id.text_death, dead)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}