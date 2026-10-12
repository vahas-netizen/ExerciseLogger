package com.example.consciousworkouts.presentation.today

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consciousworkouts.domain.AppRepository
import com.example.consciousworkouts.domain.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate


/*
Questions I am asking myself:
In my init, should I be having the completedList and OptionList updates on separate coroutines?
Is there a race condition there, or some type of issue?


 */


class TodayViewModel(
    private val repository: AppRepository,
) : ViewModel() {

    private val _todayUiState = MutableStateFlow(TodayUiState())
    val todayUiState = _todayUiState.asStateFlow()



    init {

        viewModelScope.launch {
            repository.getCompletedWorkouts().collect { completedList ->
                _todayUiState.update { it.copy(completedList = completedList) }
            }
        }

        viewModelScope.launch {
            repository.getOptionWorkouts().collect { optionList ->
                _todayUiState.update { it.copy(optionList = optionList) }

            }
        }


    }

    fun goBackDate() {
        _todayUiState.update { it.copy(selectedDate = _todayUiState.value.selectedDate.minusDays(1)) }
    }

    fun goForwardDate() {
        if (_todayUiState.value.selectedDate == LocalDate.now()) return
        _todayUiState.update {it.copy(selectedDate = _todayUiState.value.selectedDate.plusDays(1))}
    }

    fun selectDate(date: LocalDate) {
        _todayUiState.update{it.copy(selectedDate = date)}
        Log.i("The function definitely runs!", "Good!")
    }

    fun formatDate(date: LocalDate): String {
        return when (date) {
            LocalDate.now() -> "Today"
            LocalDate.now().minusDays(1) -> "Yesterday"
            else -> _todayUiState.value.selectedDate.toString()
        }

    }

    fun addCompletedWorkout(workout: Workout) {
        viewModelScope.launch {
            repository.addCompletedWorkout(workout.copy(dateCompleted = _todayUiState.value.selectedDate.toString()))
        }
    }

    fun removeCompletedWorkout(id: Int) {
        viewModelScope.launch {
            repository.removeCompletedWorkout(id)
        }
    }

    fun removeOptionWorkout(id: Int) {
        viewModelScope.launch {
            repository.removeOptionWorkout(id)
        }
    }


}


data class TodayUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val completedList: List<Workout> = emptyList(),
    val optionList: List<Workout> = emptyList(),
    val pointsGoal: Float = 100f,

)