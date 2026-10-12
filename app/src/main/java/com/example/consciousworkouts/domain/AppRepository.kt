package com.example.consciousworkouts.domain

import kotlinx.coroutines.flow.Flow


interface AppRepository {

    fun getCompletedWorkouts(): Flow<List<Workout>>

    fun getOptionWorkouts(): Flow<List<Workout>>

    suspend fun addCompletedWorkout(workout: Workout)

    suspend fun addOptionWorkout(workout: Workout)

    suspend fun removeCompletedWorkout(id: Int)

    suspend fun removeOptionWorkout(id: Int)


}