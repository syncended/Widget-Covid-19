package ru.syncended.widget_covid_19.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Total::class],
    version = 1,
    exportSchema = false
)
abstract class CovidDB : RoomDatabase() {

    abstract fun totalDao(): TotalDao

    companion object {
        private var INSTANCE: CovidDB? = null

        fun getInstance(context: Context): CovidDB = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                CovidDB::class.java,
                "covid.db"
            ).fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }

}