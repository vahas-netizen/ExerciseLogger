package com.example.consciousworkouts.presentation.today

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consciousworkouts.R
import com.example.consciousworkouts.domain.calculateTotalPoints
import com.example.consciousworkouts.domain.workoutsForDate
import com.example.consciousworkouts.presentation.components.HeaderBar
import com.example.consciousworkouts.presentation.components.WorkoutCompletedCard
import com.example.consciousworkouts.presentation.components.getIcon
import com.example.consciousworkouts.presentation.ui.theme.consciousGrey
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen
import com.example.consciousworkouts.presentation.ui.theme.primaryGreenDark
import com.example.consciousworkouts.presentation.ui.theme.progressTrack
import java.time.LocalDate


@Composable
fun TodayScreen(
    viewModel: TodayViewModel,
    navToCreate: () -> Unit
) {

    val uiState by viewModel.todayUiState.collectAsStateWithLifecycle()
    val completedWorkouts = workoutsForDate(uiState.completedList, uiState.selectedDate)
    val totalPoints = calculateTotalPoints(completedWorkouts)
    val progress = (totalPoints / uiState.pointsGoal).coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 150),
    )

    val completionScale = remember { Animatable(1f) }
    LaunchedEffect(progress >= 1f) {
        if (progress >= 1f) {
            completionScale.animateTo(
                targetValue = 1.05f,
                animationSpec = tween(120)
            )
            completionScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {


        HeaderBar(
            headline = viewModel.formatDate(uiState.selectedDate),
            leftButtonActive = true,
            leftButtonIcon = R.drawable.ic_arrow_back,
            leftButtonFunction = { viewModel.goBackDate() },
            rightButtonIcon = R.drawable.ic_arrow_forward,
            rightButtonActive = uiState.selectedDate != LocalDate.now(),
            rightButtonFunction = { viewModel.goForwardDate() },
        )



        Box(
            modifier = Modifier
                .scale(completionScale.value)
                .fillMaxWidth()
                .weight(0.5f)
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(24.dp)
                )
                .background(progressTrack),
            contentAlignment = Alignment.BottomCenter
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animatedProgress)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                primaryGreen,
                                primaryGreenDark
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$totalPoints",
                    color = Color.White,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-1).sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.35f),
                            blurRadius = 6f
                        )
                    )
                )

                Text(
                    text = "OF ${uiState.pointsGoal.toInt()} POINTS",
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                )
            }
        }





        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(0.5f)
        ) {
            item {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COMPLETED WORKOUTS",
                            color = Color.White.copy(alpha = 0.72f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.2.sp
                        )

                        Text(
                            text = "${completedWorkouts.size} completed",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 12.sp
                        )
                    }

            }

            if (completedWorkouts.isEmpty()) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = consciousGrey,
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.06f)
                        )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Text(
                                text = "No workouts yet",
                                color = Color.White.copy(alpha = 0.72f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Add one below when you're ready",
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            items(completedWorkouts, key = {it.id}) { workout ->
                    WorkoutCompletedCard(
                        workout,
                        modifier = Modifier.animateItem(
                            fadeInSpec = tween(150),
                            placementSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ),
                        longClickAction = { viewModel.removeCompletedWorkout(workout.id) })
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "QUICK ADD",
                color = Color.White.copy(alpha = 0.72f),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.2.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(uiState.optionList) { option ->
                        Surface(
                            modifier = Modifier
                                .height(48.dp)
                                .combinedClickable(
                                    onClick = { viewModel.addCompletedWorkout(option) },
                                    onLongClick = { viewModel.removeOptionWorkout(option.id) }
                                ),
                            shape = RoundedCornerShape(14.dp),
                            color = consciousGrey,
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.08f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(start = 8.dp, end = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(getIcon(option.type)),
                                    contentDescription = null,
                                    tint = primaryGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = option.name,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "+${option.points}",
                                        color = primaryGreen,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(progressTrack)
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    onClick = navToCreate
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_plus),
                        contentDescription = "Create workout option",
                        tint = primaryGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

    }


}


