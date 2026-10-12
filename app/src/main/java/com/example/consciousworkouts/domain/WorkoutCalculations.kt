package com.example.consciousworkouts.domain

import java.time.LocalDate


fun calculateTotalPoints(workouts: List<Workout>): Int {
    return workouts.sumOf{it.points}
}

fun workoutsForDate(
    workouts: List<Workout>,
    selectedDate: LocalDate
): List<Workout> {
    return workouts.filter{it.dateCompleted == selectedDate.toString()}
}