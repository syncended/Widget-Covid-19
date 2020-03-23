package ru.syncended.widget_covid_19.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Total(
    @PrimaryKey val date: String,
    @ColumnInfo val total: Int
)