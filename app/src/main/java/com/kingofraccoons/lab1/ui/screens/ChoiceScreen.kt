package com.kingofraccoons.lab1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kingofraccoons.lab1.Units
import com.kingofraccoons.lab1.UnitsViewModel
import com.kingofraccoons.lab1.getShortName

@Composable
fun ChoiceScreen(unitsViewModel: UnitsViewModel, popBackStack: () -> Boolean) {
    val currentUnits = unitsViewModel.getCurrentChangedState()?.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(Units.entries.toTypedArray()) {
            ItemUnits(isCurrent = it == currentUnits?.value, units = it) {
                unitsViewModel.actionToSetNewUnit?.invoke(it)
                popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemUnits(isCurrent: Boolean, units: Units, setUnits: () -> Unit) {
    Card(
        setUnits,
        if (isCurrent)
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(1.5.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp))
        else
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getShortName(
                    start = units.shortName,
                    upper = units.upperShortText
                ),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}