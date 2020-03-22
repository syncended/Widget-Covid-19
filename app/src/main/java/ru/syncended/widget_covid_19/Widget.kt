package ru.syncended.widget_covid_19


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.syncended.widget_covid_19.api.NetworkService

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

    //Todo: Calculate today amount
    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        var count = ""
        val job = GlobalScope.launch(Dispatchers.IO) {
//            val retrofit = NetworkService.retrofit()
//            val response = retrofit.getCount()
//            count = response[0].caseCount
        }

        job.invokeOnCompletion {
            if (it != null) {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            } else {
                val views = RemoteViews(context.packageName, R.layout.widget)
                views.setTextViewText(R.id.text_total, count)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }

    companion object {
        var lastDate = ""
    }

}