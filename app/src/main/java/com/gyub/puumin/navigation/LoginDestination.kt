package com.gyub.puumin.navigation

import androidx.annotation.StringRes
import com.gyub.puumin.R

/**
 * 로그인 Destination
 *
 * @author   Gyub
 * @created  2024/06/25
 */
enum class LoginDestination(@StringRes val title: Int) {
    LOGIN_HOME(R.string.login_home),
    LOGIN_EMAIL(R.string.login_id_pw)
}