package com.kingofraccoons.lab1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kingofraccoons.lab1.Action
import com.kingofraccoons.lab1.CalculatorAction
import com.kingofraccoons.lab1.UnitsViewModel
import com.kingofraccoons.lab1.getShortName
import com.kingofraccoons.lab1.ui.theme.secondaryColor

@Composable
fun MainScreen(unitsViewModel: UnitsViewModel, navigateToChoice: () -> Unit) {
    val firstUnit = unitsViewModel.currentFirstUnitFlow.collectAsState()
    val secondUnit = unitsViewModel.currentSecondUnitFlow.collectAsState()
    val amountFirstUnit = unitsViewModel.amountCurrentFirstUnitFlow.collectAsState()
    val amountSecondUnit = unitsViewModel.getAmountCurrentSecondUnitFlow().collectAsState(initial = 0.0)

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.spacedBy(2.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Box(
                Modifier
                    .weight(1f)
                    .aspectRatio(1f, true)
                    .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        unitsViewModel.onEvent(Action.ChoiceFromUnit)
                        navigateToChoice()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getShortName(
                        start = firstUnit.value.shortName,
                        upper = firstUnit.value.upperShortText
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }


            Row(
                Modifier
                    .weight(3f)
                    .aspectRatio(3f, true)
                    .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(16.dp)),
                Arrangement.spacedBy(4.dp),
                Alignment.CenterVertically
            ) {
                Text(
                    text = "Из",
                    Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = secondaryColor,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = (amountFirstUnit.value.toDoubleOrNull() ?: 0.0).toString(),
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End
                )
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Box(
                Modifier
                    .weight(1f)
                    .aspectRatio(1f, true)
                    .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        unitsViewModel.onEvent(Action.ChoiceToUnit)
                        navigateToChoice()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = getShortName(
                        start = secondUnit.value.shortName,
                        upper = secondUnit.value.upperShortText
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Row(
                Modifier
                    .weight(3f)
                    .aspectRatio(3f, true)
                    .background(MaterialTheme.colorScheme.onSecondary, RoundedCornerShape(16.dp)),
                Arrangement.spacedBy(4.dp),
                Alignment.CenterVertically
            ) {
                Text(
                    text = "В",
                    Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = secondaryColor,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = amountSecondUnit.value.toString(),
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End
                )
            }
        }

        val listgrid =
            listOf("7", "8", "9", "C", "4", "5", "6", "del", "7", "8", "9", ",", "0", "c")

        val spaceBetweenButtons = 2.dp

        val configuration = LocalConfiguration.current
        val screenWidth = (configuration.screenWidthDp.dp - spaceBetweenButtons * 4 - 16.dp) / 4f

        Spacer(Modifier.weight(1f))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenButtons),
            verticalArrangement = Arrangement.spacedBy(spaceBetweenButtons)
        ) {
            itemsIndexed(
                CalculatorAction.entries.toTypedArray(),
                span = { _, it ->
                    GridItemSpan(it.spanCount)
                }
            ) { index, it ->
                if (it.spanCount == 4)
                    Box(
                        Modifier
                            .size(screenWidth * 4, screenWidth)
                            .background(getColorButton(index), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                unitsViewModel.onCalculatorAction(it)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.symbol,
                            style = MaterialTheme.typography.titleLarge,
                            color = getColorText(index),
                            textAlign = TextAlign.Center
                        )
                    }
                else
                    Box(
                        Modifier
                            .size(screenWidth)
                            .background(getColorButton(index), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                unitsViewModel.onCalculatorAction(it)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.symbol,
                            style = MaterialTheme.typography.titleLarge,
                            color = getColorText(index),
                            textAlign = TextAlign.Center
                        )
                    }
            }
        }
    }
}

@Composable
fun getColorButton(index: Int) = when (index) {
    3, 7, 11, 13 -> MaterialTheme.colorScheme.onSurface
    else -> MaterialTheme.colorScheme.onSecondary
}

@Composable
fun getColorText(index: Int) = when (index) {
    3, 7, 11, 13 -> Color.White
    else -> MaterialTheme.colorScheme.onBackground
}