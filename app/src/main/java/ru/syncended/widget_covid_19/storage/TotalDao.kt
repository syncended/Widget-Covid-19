package ru.syncended.widget_covid_19.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TotalDao {
    @Query("SELECT * FROM total WHERE date = :date")
    fun get(date: String): Total?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)
}