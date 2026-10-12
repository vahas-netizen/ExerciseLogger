package com.example.consciousworkouts.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.consciousworkouts.domain.WorkoutType


@Entity(tableName = "completed_db")
data class CompletedEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val type: WorkoutType,
    val dateCompleted: String?
)

