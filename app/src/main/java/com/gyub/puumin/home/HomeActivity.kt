package com.gyub.puumin.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gyub.design.theme.PuumInTheme
import com.gyub.puumin.R
import com.gyub.puumin.ui.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * 홈화면
 */
@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoubleBackToExit()

            PuumInTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }


    @Composable
    fun DoubleBackToExit() {
        val backPressedOnce by viewModel.backPressedOnce.collectAsStateWithLifecycle()

        BackHandler {
            if (backPressedOnce) {
                finish()
            } else {
                viewModel.updateBackPressed()
                Toast.makeText(this, getString(R.string.double_back_finish), Toast.LENGTH_SHORT).show()
            }
        }
    }
}