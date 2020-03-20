package ru.syncended.widget_covid_19.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountDao {
    @Query("SELECT * FROM count WHERE date = :date")
    fun get(date: String): Count?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(count: Count)
}