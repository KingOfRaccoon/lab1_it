package com.kingofraccoons.lab1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UnitsViewModel : ViewModel() {
    private val _currentFirstUnitFlow = MutableStateFlow(Units.Metrs)
    val currentFirstUnitFlow = _currentFirstUnitFlow.asStateFlow()

    private val _currentSecondUnitFlow = MutableStateFlow(Units.Acres)
    val currentSecondUnitFlow = _currentSecondUnitFlow.asStateFlow()

    private val _amountCurrentFirstUnitFlow = MutableStateFlow("")
    val amountCurrentFirstUnitFlow = _amountCurrentFirstUnitFlow.asStateFlow()

    private var currentChangedState: MutableStateFlow<Units>? = null

    var actionToSetNewUnit: ((Units) -> Unit)? = null

    fun getAmountCurrentSecondUnitFlow() =
        combine(
            currentFirstUnitFlow,
            currentSecondUnitFlow,
            amountCurrentFirstUnitFlow
        ) { firstUnit, secondUnit, amountFirstUnit ->
            (firstUnit.amount / secondUnit.amount) * (amountFirstUnit.toDoubleOrNull() ?: 0.0)
        }

    fun getCurrentChangedState() = currentChangedState?.asStateFlow()

    private fun setFirstUnitFlow(units: Units) {
        viewModelScope.launch {
            _currentFirstUnitFlow.emit(units)
        }
    }

    private fun setSecondUnitFlow(units: Units) {
        viewModelScope.launch {
            _currentSecondUnitFlow.emit(units)
        }
    }

    fun onEvent(action: Action) {
        when (action) {
            Action.ChoiceFromUnit -> {
                currentChangedState = _currentFirstUnitFlow
                actionToSetNewUnit = ::setFirstUnitFlow
            }

            Action.ChoiceToUnit -> {
                currentChangedState = _currentSecondUnitFlow
                actionToSetNewUnit = ::setSecondUnitFlow
            }
        }
    }

    fun onCalculatorAction(action: CalculatorAction){
        viewModelScope.launch {
            _amountCurrentFirstUnitFlow.update {
                action.action(it)
            }
        }
    }
}