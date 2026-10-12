package com.example.consciousworkouts.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {

    @Insert
    suspend fun addCompletedWorkout(workout: CompletedEntity)

    @Insert
    suspend fun addOptionWorkout(workout: OptionEntity)

    @Query("SELECT * FROM completed_db")
    fun getCompletedWorkouts(): Flow<List<CompletedEntity>>

    @Query("SELECT * FROM option_db")
    fun getOptionWorkouts(): Flow<List<OptionEntity>>

    @Query("DELETE FROM completed_db WHERE id = :id")
    suspend fun removeCompletedWorkout(id: Int)

    @Query("DELETE FROM option_db WHERE id = :id")
    suspend fun removeOptionWorkout(id: Int)


}