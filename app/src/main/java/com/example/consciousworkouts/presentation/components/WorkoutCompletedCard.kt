package com.example.consciousworkouts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consciousworkouts.R
import com.example.consciousworkouts.domain.Workout
import com.example.consciousworkouts.domain.WorkoutType
import com.example.consciousworkouts.presentation.ui.theme.consciousGrey
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen


@Composable
fun WorkoutCompletedCard(workout: Workout, modifier: Modifier = Modifier, longClickAction: () -> Unit ) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(consciousGrey)
            .combinedClickable(
                onClick = {},
                onLongClick = longClickAction
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(14.dp),
                color = Color.White.copy(alpha = 0.06f)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(primaryGreen.copy(alpha = 0.14f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(getIcon(workout.type)),
                contentDescription = null,
                tint = primaryGreen,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = workout.name,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(primaryGreen.copy(alpha = 0.12f))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Text(
                text = "+${workout.points}",
                color = primaryGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }


}


fun getIcon(workoutType: WorkoutType): Int {
    return when (workoutType) {
        WorkoutType.CARDIO -> R.drawable.ic_run
        WorkoutType.MOBILITY -> R.drawable.ic_meditation
        WorkoutType.STRENGTH -> R.drawable.ic_dumbbell
    }
}