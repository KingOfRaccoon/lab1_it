package com.kingofraccoons.lab1

enum class CalculatorAction(
    val symbol: String,
    val spanCount: Int = 1,
    val action: (String) -> String = { it + symbol }
) {
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Clear("c", action = { "" }),
    Four("4"),
    Five("5"),
    Six("6"),
    Delete(
        "del",
        action = {
            if (it.isNotEmpty())
                it.slice(IntRange(0, it.lastIndex - 1))
            else
                it
        }),
    One("1"),
    Two("2"),
    Three("3"),
    Сomma("."),
    Zero("0", 4, { if (it.contains(".") && it.last() != '0') it + "0" else it }),
}