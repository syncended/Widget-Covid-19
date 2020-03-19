package ru.syncended.widget_covid_19


import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
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
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    var text = ""
    val job = GlobalScope.launch(Dispatchers.IO) {
        val retrofit = NetworkService.retrofit()
        val response = retrofit.getCount(System.currentTimeMillis())
        text = response[0].caseCount
        Log.e("tag", "$response")
    }

    job.invokeOnCompletion {
        Log.e("tag", "$it")
        if (it != null) {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        } else {
            val views = RemoteViews(context.packageName, R.layout.widget)
            views.setTextViewText(R.id.text_count, text)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}