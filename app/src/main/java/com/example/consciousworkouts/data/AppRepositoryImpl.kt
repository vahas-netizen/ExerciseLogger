package com.example.consciousworkouts.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.consciousworkouts.domain.AppRepository
import com.example.consciousworkouts.domain.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate


class AppRepositoryImpl(private val dao: WorkoutDao): AppRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addCompletedWorkout(workout: Workout) {
        dao.addCompletedWorkout(workout.toCompletedEntity())
    }

    override suspend fun addOptionWorkout(workout: Workout) {
        dao.addOptionWorkout(workout.toOptionEntity())
    }

    override fun getCompletedWorkouts(): Flow<List<Workout>> {
        return dao.getCompletedWorkouts().map { completedList ->
            completedList.map {completedEntity ->
                completedEntity.toWorkout()
            }
        }
    }

    override fun getOptionWorkouts(): Flow<List<Workout>> {
        return dao.getOptionWorkouts().map {optionList ->
            optionList.map{optionEntity ->
                optionEntity.toWorkout()
            }
        }
    }

    override suspend fun removeOptionWorkout(id: Int) {
        dao.removeOptionWorkout(id)
    }

    override suspend fun removeCompletedWorkout(id: Int) {
        dao.removeCompletedWorkout(id)
    }


}




fun Workout.toCompletedEntity(): CompletedEntity  {
    return CompletedEntity(
        id = 0,
        type = this.type,
        points = this.points,
        name = this.name,
        dateCompleted = this.dateCompleted
    )

}


fun Workout.toOptionEntity(): OptionEntity {
    return OptionEntity(
        id = 0,
        type = this.type,
        points = this.points,
        name = this.name,
    )
}

fun OptionEntity.toWorkout(): Workout {
    return Workout(
        id = this.id,
        type = this.type,
        points = this.points,
        name = this.name
    )
}


fun CompletedEntity.toWorkout(): Workout {
    return Workout(
        id = this.id,
        type = this.type,
        points = this.points,
        name = this.name,
        dateCompleted = this.dateCompleted
    )
}




