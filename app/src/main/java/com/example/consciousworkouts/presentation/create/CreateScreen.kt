package com.example.consciousworkouts.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.consciousworkouts.domain.Workout
import com.example.consciousworkouts.domain.WorkoutType
import com.example.consciousworkouts.presentation.components.HeaderBar
import com.example.consciousworkouts.presentation.components.WorkoutCompletedCard
import com.example.consciousworkouts.presentation.components.getIcon
import com.example.consciousworkouts.presentation.ui.theme.consciousGrey
import com.example.consciousworkouts.presentation.ui.theme.onPrimaryGreen
import com.example.consciousworkouts.presentation.ui.theme.primaryGreen
import com.example.consciousworkouts.presentation.ui.theme.progressTrack
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(viewModel: CreateViewModel = koinViewModel(), navBack: () -> Unit) {
    val uiState by viewModel.createUiState.collectAsStateWithLifecycle()
    var quantityDropDownExpanded by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = progressTrack,
        unfocusedContainerColor = progressTrack,
        focusedBorderColor = primaryGreen,
        unfocusedBorderColor = Color.White.copy(alpha = 0.08f),
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedLabelColor = primaryGreen,
        unfocusedLabelColor = Color.White.copy(alpha = 0.55f),
        cursorColor = primaryGreen,
        focusedTrailingIconColor = primaryGreen,
        unfocusedTrailingIconColor = Color.White.copy(alpha = 0.55f)
    )

    val previewName = when {
        uiState.nameUserEntry.isBlank() -> "Workout preview"
        uiState.qtyUserEntry.isBlank() -> uiState.nameUserEntry
        else -> "${uiState.nameUserEntry} (${uiState.qtyUserEntry} ${uiState.quantityType.displayName()})"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HeaderBar(
            headline = "New Workout",
            leftButtonFunction = navBack,
            leftButtonActive = true
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            /*
            Text(
                text = "Create a reusable workout for Quick Add.",
                color = Color.White.copy(alpha = 0.55f),
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            */


            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionLabel("WORKOUT DETAILS")

                FormSection {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.nameUserEntry,
                        onValueChange = { viewModel.updateNameEntry(it) },
                        label = { Text("Workout name") },
                        placeholder = { Text("e.g. Pull ups") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = fieldColors
                    )

                    Text(
                        text = "WORKOUT TYPE",
                        color = Color.White.copy(alpha = 0.55f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        WorkoutType.entries.forEach { type ->
                            val selected = uiState.exerciseType == type

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(78.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (selected) {
                                            primaryGreen.copy(alpha = 0.14f)
                                        } else {
                                            progressTrack
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selected) {
                                            primaryGreen.copy(alpha = 0.8f)
                                        } else {
                                            Color.White.copy(alpha = 0.08f)
                                        },
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { viewModel.updateExerciseType(type) }
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(getIcon(type)),
                                    contentDescription = null,
                                    tint = if (selected) {
                                        primaryGreen
                                    } else {
                                        Color.White.copy(alpha = 0.6f)
                                    },
                                    modifier = Modifier.size(22.dp)
                                )

                                Text(
                                    text = type.displayName(),
                                    color = if (selected) {
                                        Color.White
                                    } else {
                                        Color.White.copy(alpha = 0.65f)
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionLabel("QUANTITY")

                FormSection {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.weight(0.42f),
                            value = uiState.qtyUserEntry,
                            onValueChange = { viewModel.updateQuantityEntry(it) },
                            label = { Text("Amount") },
                            placeholder = { Text("10") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(14.dp),
                            colors = fieldColors
                        )

                        ExposedDropdownMenuBox(
                            modifier = Modifier.weight(0.58f),
                            expanded = quantityDropDownExpanded,
                            onExpandedChange = {
                                quantityDropDownExpanded = !quantityDropDownExpanded
                            }
                        ) {
                            OutlinedTextField(
                                value = uiState.quantityType.displayName(),
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Unit") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = quantityDropDownExpanded
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                shape = RoundedCornerShape(14.dp),
                                colors = fieldColors
                            )

                            ExposedDropdownMenu(
                                containerColor = consciousGrey,
                                expanded = quantityDropDownExpanded,
                                onDismissRequest = { quantityDropDownExpanded = false }
                            ) {
                                QuantityType.entries.forEach { quantityType ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = quantityType.displayName(),
                                                color = Color.White
                                            )
                                        },
                                        onClick = {
                                            viewModel.updateQuantityType(quantityType)
                                            quantityDropDownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionLabel("POINTS AWARDED")

                FormSection {
                    Text(
                        text = "Choose how much this workout contributes to your daily target.",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items((5..100 step 5).toList()) { points ->
                            val selected = uiState.selectedPoints == points

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (selected) {
                                            primaryGreen.copy(alpha = 0.18f)
                                        } else {
                                            progressTrack
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (selected) {
                                            primaryGreen
                                        } else {
                                            Color.White.copy(alpha = 0.08f)
                                        },
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { viewModel.updateSelectedPoints(points) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$points",
                                    color = if (selected) {
                                        Color.White
                                    } else {
                                        Color.White.copy(alpha = 0.6f)
                                    },
                                    fontWeight = if (selected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Medium
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SectionLabel("PREVIEW")

                WorkoutCompletedCard(
                    workout = Workout(
                        name = previewName,
                        points = uiState.selectedPoints,
                        type = uiState.exerciseType,
                        id = 0,
                    ),
                    longClickAction = {}
                )
            }
        }

        Button(
            onClick = { viewModel.addWorkoutOption(); navBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
        ) {
            Text(
                text = "Create Workout",
                color = onPrimaryGreen,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.72f),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 1.2.sp,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun FormSection(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(consciousGrey)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.06f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}

private fun Enum<*>.displayName(): String =
    name.lowercase().replaceFirstChar { it.uppercase() }
