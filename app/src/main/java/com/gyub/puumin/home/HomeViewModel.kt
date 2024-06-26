package com.gyub.puumin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel
 *
 * @author   Gyub
 * @created  2024/06/16
 */
@HiltViewModel
class HomeViewModel
@Inject constructor(
) : ViewModel() {

    private val _backPressedOnce = MutableStateFlow(false)
    val backPressedOnce: StateFlow<Boolean> get() = _backPressedOnce

    /**
     * 뒤로가기 이벤트 감지
     */
    fun updateBackPressed() {
        viewModelScope.launch {
            if (_backPressedOnce.value) {
                _backPressedOnce.value = false
            } else {
                _backPressedOnce.value = true
                delay(2000)
                _backPressedOnce.value = false
            }
        }
    }
}

