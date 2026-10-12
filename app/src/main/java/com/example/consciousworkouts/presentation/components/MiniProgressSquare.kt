package com.example.consciousworkouts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen
import com.example.consciousworkouts.presentation.ui.theme.primaryGreenDark
import com.example.consciousworkouts.presentation.ui.theme.progressTrack
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun MiniProgressSquare(
    totalPoints: Int,
    pointsGoal: Int,
    isToday: Boolean,
    day: LocalDate?,
    selectSquareAction: () -> Unit
) {

    val progress = (totalPoints / pointsGoal.toFloat()).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable {selectSquareAction()}
            .border(
                width = if (isToday) 1.5.dp else 1.dp,
                color = Color.White.copy(alpha = if (isToday) 0.8f else 0.08f),
                shape = RoundedCornerShape(24.dp)
            )
            .background(progressTrack),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(progress)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            primaryGreenDark,
                            primaryGreen
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${day?.format(DateTimeFormatter.ofPattern("MMM d"))}",
                color = Color.White,
                fontSize = 12.sp,
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
                text = "$totalPoints",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 10.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.35f),
                        blurRadius = 6f
                    )
                )
            )

        }
    }


}


@Preview
@Composable
fun ProgressSquarePreview() {
    //  ProgressSquare(2, 100f)
}