package com.gyub.puumin.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyub.common.const.UserPrefKey.TOKEN
import com.gyub.data.datastore.UserPreferencesRepository
import com.gyub.puumin.base.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 로그인 뷰모델
 *
 * @author   Gyub
 * @created  2024/06/25
 */
@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _backPressedOnce = MutableStateFlow(false)
    val backPressedOnce: StateFlow<Boolean> get() = _backPressedOnce

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val loginUiState: StateFlow<UiState> = _loginUiState.asStateFlow()

    /**
     * 이메일 세팅
     *
     * @param email
     */
    fun updateEmail(email: String) {
        _email.value = email
    }

    /**
     * 비밀번호 세팅
     *
     * @param password
     */
    fun updatePassword(password: String) {
        _password.value = password
    }

    /**
     * 로그인
     */
    fun login() {
        viewModelScope.launch {
            flow {
                delay(200)
                emit(UiState.Success)
            }.collect {
                _loginUiState.value = it
                userPreferencesRepository.saveString(TOKEN, "testToken")
            }
        }
    }

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