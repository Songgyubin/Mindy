package com.gyub.puumin.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gyub.puumin.R
import com.gyub.puumin.navigation.SignUpDestination

/**
 * Top App Bar
 *
 * @author   Gyub
 * @created  2024/06/19
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuumInCenterAlignedAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    navigateUp: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(title)) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
    )
}