package ru.syncended.widget_covid_19.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Count::class],
    version = 1,
    exportSchema = false
)
abstract class CovidDB : RoomDatabase() {

    abstract fun countDao(): CountDao

    companion object {
        private var INSTANCE: CovidDB? = null
        fun getInstance(context: Context): CovidDB = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                CovidDB::class.java,
                "covid.db"
            ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}