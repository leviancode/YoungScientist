package com.leviancode.youngscientist.ui.common

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClick() }) {
                navigationIcon?.invoke()
            }
        }
    )
}