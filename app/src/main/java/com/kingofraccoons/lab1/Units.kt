package com.kingofraccoons.lab1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

enum class Units(val shortName: String, val upperShortText: String, val amount: Double) {
    Feets("ft", "2", 0.092903),
    Yards("yd", "2", 0.836127),
    Millimeters("cm", "2", 0.000001),
    Centimeters("cm", "2", 0.0001),
    Decimeters("dm", "2", 0.01),
    Metrs("m", "2", 1.0),
    Ars("ar", "", 100.0),
    Acres("acr", "", 4046.86),
    Hectares("hec", "", 10000.0)
}

@Composable
fun getShortName(start: String, upper: String) = buildAnnotatedString {
    withStyle(
        SpanStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 22.sp,
        )
    ) {
        append(start)
    }
    withStyle(
        SpanStyle(
            baselineShift = BaselineShift.Superscript, // added line
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    ) {
        append(upper)
    }
}