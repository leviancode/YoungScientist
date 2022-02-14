package com.leviancode.youngscientist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.leviancode.youngscientist.ui.NavigationComponent
import com.leviancode.youngscientist.ui.theme.YoungScientistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoungScientistTheme {
                NavigationComponent()
            }
        }
    }
}