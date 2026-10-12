package com.example.consciousworkouts.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.consciousworkouts.domain.WorkoutType
import java.time.LocalDate


@Entity(tableName = "option_db")
data class OptionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val points: Int,
    val type: WorkoutType,
)