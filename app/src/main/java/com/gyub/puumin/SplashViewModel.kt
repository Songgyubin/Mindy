package com.gyub.puumin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.data.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * 스플래시 뷰모델
 *
 * @author   Gyub
 * @created  2024/06/26
 */
@HiltViewModel
class SplashViewModel
@Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    val token: StateFlow<String> = userPreferencesRepository.token
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ""
        )

}