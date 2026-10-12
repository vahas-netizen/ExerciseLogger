package com.example.consciousworkouts.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consciousworkouts.domain.AppRepository
import com.example.consciousworkouts.domain.Workout
import com.example.consciousworkouts.domain.WorkoutType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class CreateViewModel(private val repository: AppRepository): ViewModel() {

    private val _createUiState = MutableStateFlow(CreateUiState())
    val createUiState = _createUiState.asStateFlow()


    fun updateQuantityEntry(entry: String) = _createUiState.update{it.copy(qtyUserEntry = entry)}

    fun updateNameEntry(entry: String) = _createUiState.update{it.copy(nameUserEntry = entry)}

    fun updateQuantityType(selectedQuantityType: QuantityType) = _createUiState.update{it.copy(quantityType = selectedQuantityType)}

    fun updateExerciseType(selectedExerciseType: WorkoutType) = _createUiState.update{it.copy(exerciseType = selectedExerciseType)}

    fun updateSelectedPoints(pointsSelection: Int) = _createUiState.update{it.copy(selectedPoints = pointsSelection)}


    fun addWorkoutOption() {
        viewModelScope.launch {
            repository.addOptionWorkout(Workout(
                name = "${_createUiState.value.nameUserEntry} (${_createUiState.value.qtyUserEntry} ${_createUiState.value.quantityType.capitalizeFirst()})",
                points = _createUiState.value.selectedPoints,
                type = _createUiState.value.exerciseType
            ))
        }
    }

    private fun QuantityType.capitalizeFirst(): String {
        return this.toString().lowercase().replaceFirstChar{it.uppercase()}
    }







}








data class CreateUiState(
    val qtyUserEntry: String = "",
    val nameUserEntry: String = "",
    val quantityType: QuantityType = QuantityType.REPS,
    val exerciseType: WorkoutType = WorkoutType.STRENGTH,
    val selectedPoints: Int = 1
)



enum class QuantityType {
    STEPS,
    REPS,
    SET,
    MINUTES,
    KILOMETRES
}