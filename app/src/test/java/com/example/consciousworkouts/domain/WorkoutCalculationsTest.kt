package com.example.consciousworkouts.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.LocalDate

class WorkoutCalculationsTest {

    @Test
    fun calculateTotalPoints_returnsSumOfWorkoutPoints() {
        val workouts = listOf(
            Workout(
                name = "Run",
                points = 20,
                type = WorkoutType.CARDIO
            ),
            Workout(
                name = "Push Ups",
                points = 30,
                type = WorkoutType.STRENGTH
            )
        )

        val result = calculateTotalPoints(workouts)

        assertEquals(50, result)


    }

    @Test
    fun calculateTotalPoints_returnsZeroForEmptyList() {
        val result = calculateTotalPoints(emptyList())

        assertEquals(0, result)
    }


    @Test
    fun filterWorkoutByDate_returnsWorkoutsEqualToDate() {

        val workouts = listOf(
            Workout(
                name = "Walking",
                points = 10,
                type = WorkoutType.CARDIO,
                dateCompleted = LocalDate.now().toString()
            ),
            Workout(
                name = "Pull Ups",
                points = 10,
                type = WorkoutType.STRENGTH,
                dateCompleted = LocalDate.now().minusDays(1).toString()
            )
        )

        val result = workoutsForDate(workouts, LocalDate.now())

        assertEquals(
            listOf(
                Workout(
                    name = "Walking",
                    points = 10,
                    type = WorkoutType.CARDIO,
                    dateCompleted = LocalDate.now().toString()
                )
            ),
            result
        )


    }


}