package com.example.consciousworkouts.presentation.past

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consciousworkouts.R
import com.example.consciousworkouts.presentation.components.HeaderBar
import com.example.consciousworkouts.presentation.today.TodayViewModel
import com.example.consciousworkouts.presentation.ui.theme.consciousGrey
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen
import com.example.consciousworkouts.presentation.ui.theme.primaryGreenDark
import com.example.consciousworkouts.presentation.ui.theme.progressTrack
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Composable
fun PastScreen(
    viewModel: PastViewModel = koinViewModel(),
    navToToday: () -> Unit,
    todayViewModel: TodayViewModel
) {

    val uiState by viewModel.pastUiState.collectAsStateWithLifecycle()
    val today = LocalDate.now()
    val selectedMonth = uiState.selectedYearMonth
    val workoutsByDate = uiState.completedList.groupBy { it.dateCompleted }
    val visibleDays = (1..selectedMonth.lengthOfMonth()).map(selectedMonth::atDay)
    val elapsedDays = visibleDays.filter { it <= today }
    val monthlyPoints = elapsedDays.sumOf { day ->
        workoutsByDate[day.toString()].orEmpty().sumOf { it.points }
    }
    val activeDays = elapsedDays.count { day ->
        workoutsByDate[day.toString()].orEmpty().isNotEmpty()
    }
    val goalDays = elapsedDays.count { day ->
        workoutsByDate[day.toString()].orEmpty().sumOf { it.points } >= DAILY_POINTS_GOAL
    }
    val leadingEmptyDays = selectedMonth.atDay(1).dayOfWeek.value - 1
    val calendarItems: List<LocalDate?> = List(leadingEmptyDays) { null } + visibleDays

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        HeaderBar(
            headline = selectedMonth.format(MONTH_FORMATTER),
            leftButtonActive = true,
            leftButtonFunction = {
                viewModel.updateSelectedYearMonth(selectedMonth.minusMonths(1))
            },
            leftButtonIcon = R.drawable.ic_arrow_back,
            rightButtonActive = selectedMonth != YearMonth.now(),
            rightButtonFunction = {
                viewModel.updateSelectedYearMonth(selectedMonth.plusMonths(1))
            },
            rightButtonIcon = R.drawable.ic_arrow_forward
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "MONTHLY OVERVIEW",
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.3.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = consciousGrey,
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MonthStat(monthlyPoints.toString(), "POINTS", Modifier.weight(1f))
                    MonthStat(activeDays.toString(), "ACTIVE DAYS", Modifier.weight(1f))
                    MonthStat(goalDays.toString(), "GOALS MET", Modifier.weight(1f))
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            WEEKDAY_LABELS.forEach { label ->
                Text(
                    text = label,
                    color = Color.White.copy(alpha = 0.42f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(calendarItems) { day ->
                if (day == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.82f)
                    )
                } else {
                    val points = workoutsByDate[day.toString()].orEmpty().sumOf { it.points }
                    CalendarDay(
                        day = day,
                        points = points,
                        isToday = day == today,
                        isEnabled = day <= today,
                        onClick = {
                            todayViewModel.selectDate(day)
                            navToToday()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = value,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.48f),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
private fun CalendarDay(
    day: LocalDate,
    points: Int,
    isToday: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val progress = (points / DAILY_POINTS_GOAL.toFloat()).coerceIn(0f, 1f)
    val shape = RoundedCornerShape(13.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.82f)
            .clip(shape)
            .clickable(enabled = isEnabled, onClick = onClick)
            .background(if (isEnabled) progressTrack else progressTrack.copy(alpha = 0.32f))
            .then(
                if (isToday) {
                    Modifier.background(
                        brush = Brush.verticalGradient(
                            listOf(primaryGreen.copy(alpha = 0.18f), Color.Transparent)
                        )
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (isEnabled && progress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(progress)
                    .background(
                        Brush.verticalGradient(
                            listOf(primaryGreen, primaryGreenDark)
                        )
                    )
            )
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = shape,
            color = Color.Transparent,
            border = BorderStroke(
                width = if (isToday) 1.5.dp else 1.dp,
                color = when {
                    isToday -> primaryGreen
                    isEnabled -> Color.White.copy(alpha = 0.07f)
                    else -> Color.Transparent
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = day.dayOfMonth.toString(),
                    color = Color.White.copy(alpha = if (isEnabled) 0.95f else 0.25f),
                    fontSize = 14.sp,
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium
                )
                if (isEnabled) {
                    Text(
                        text = if (points > 0) points.toString() else "—",
                        color = Color.White.copy(alpha = if (points > 0) 0.72f else 0.28f),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private const val DAILY_POINTS_GOAL = 100
private val MONTH_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy")
private val WEEKDAY_LABELS = listOf("M", "T", "W", "T", "F", "S", "S")