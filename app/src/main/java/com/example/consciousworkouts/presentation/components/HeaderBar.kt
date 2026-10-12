package com.example.consciousworkouts.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.consciousworkouts.R


@Composable
fun HeaderBar(
    headline: String,
    leftButtonActive: Boolean = false,
    leftButtonFunction: () -> Unit = {},
    leftButtonIcon: Int = R.drawable.ic_arrow_back,
    rightButtonActive: Boolean = false,
    rightButtonFunction: () -> Unit = {},
    rightButtonIcon: Int = R.drawable.ic_plus,
) {

    Box(modifier = Modifier.fillMaxWidth()) {

        if (leftButtonActive) {
            IconButton(
                onClick = leftButtonFunction,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(leftButtonIcon),
                    contentDescription = "Left Function",
                    tint = Color.White
                )

            }
        }



            Text(
                modifier = Modifier.align(Alignment.Center),
                text = headline,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )




        if (rightButtonActive) {
            IconButton(
                onClick = rightButtonFunction,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(rightButtonIcon),
                    contentDescription = "Right Function",
                    tint = Color.White
                )

            }


        }


    }


}


