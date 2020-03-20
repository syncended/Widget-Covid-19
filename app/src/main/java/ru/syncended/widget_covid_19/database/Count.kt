package ru.syncended.widget_covid_19.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Count (
    @ColumnInfo val caseCount: String,
    @PrimaryKey val date: String
)