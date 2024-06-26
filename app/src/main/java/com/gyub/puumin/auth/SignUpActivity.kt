package com.gyub.puumin.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gyub.design.theme.PuumInTheme
import com.gyub.puumin.auth.ui.EMAIL_AUTH_ROOT
import com.gyub.puumin.auth.ui.PASSWORD_ROUTE
import com.gyub.puumin.auth.ui.USER_INFO_ROUTE
import com.gyub.puumin.navigation.SignUpDestination
import com.gyub.puumin.navigation.SignUpNavHost
import com.gyub.puumin.ui.PuumInCenterAlignedAppBar
import dagger.hilt.android.AndroidEntryPoint

/**
 * 회원가입 Activity
 *
 * @author   Gyub
 * @created  2024/06/19
 */
@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PuumInTheme {
                SignUpScreen()
            }
        }
    }

    @Composable
    fun SignUpScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value
            ?.destination

        val currentScreen = when (currentDestination?.route) {
            EMAIL_AUTH_ROOT -> SignUpDestination.EMAIL_AUTH
            USER_INFO_ROUTE -> SignUpDestination.USER_INFO
            PASSWORD_ROUTE -> SignUpDestination.PASSWORD
            else -> SignUpDestination.EMAIL_AUTH
        }

        Scaffold(
            topBar = {
                PuumInCenterAlignedAppBar(
                    title = currentScreen.title,
                    navigateUp = {
                        if (currentScreen == SignUpDestination.EMAIL_AUTH) {
                            finish()
                        } else {
                            navController.navigateUp()
                        }
                    }
                )
            },
        ) { contentPadding ->
            SignUpNavHost(
                modifier = modifier.padding(contentPadding),
                navController = navController
            )
        }
    }
}