package com.example.consciousworkouts.presentation.past

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consciousworkouts.domain.AppRepository
import com.example.consciousworkouts.domain.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth


class PastViewModel(repository: AppRepository): ViewModel() {

    private val _pastUiState = MutableStateFlow(PastUiState())
    val pastUiState = _pastUiState.asStateFlow()







    init {
        viewModelScope.launch {
            repository.getCompletedWorkouts().collect { completedList ->
                _pastUiState.update { it.copy(completedList = completedList) }
            }
        }
    }


    fun updateSelectedYearMonth(newYearMonth: YearMonth) {
        _pastUiState.update{it.copy(selectedYearMonth = newYearMonth)}

    }












}





data class PastUiState(
    val selectedYearMonth: YearMonth = YearMonth.now(),
    val completedList: List<Workout> = emptyList()

)