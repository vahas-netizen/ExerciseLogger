package com.example.consciousworkouts.domain


data class Workout(
    val id: Int = 0,
    val name: String,
    val points: Int,
    val type: WorkoutType,
    val dateCompleted: String? = null
)


enum class WorkoutType {
    CARDIO,
    STRENGTH,
    MOBILITY
}