package com.example.invoice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.invoice.ui.BaseActivity
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.utils.LocalScreenSize
import com.example.invoice.ui.utils.getScreenSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /** The status bar color which is dynamic */
            val defaultStatusBarColor = MaterialTheme.colors.background.toArgb()
            var statusBarColor by remember { mutableStateOf(defaultStatusBarColor) }
            window.statusBarColor = statusBarColor

            val navController = rememberNavController()

//            /** Getting screen size */
//            val size = LocalContext.current.getScreenSize()

            InvoiceTheme {
                CompositionLocalProvider(
//                    LocalScreenSize provides size,
                    LocalNavHost provides navController
                ) {
                    // A surface container using the 'background' color from the theme
                    androidx.compose.material.Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = androidx.compose.material.MaterialTheme.colors.background
                    ) {
                        HolderScreens(
                            onStatusBarColorChange = {
                                /** Updating the color of the status bar */
                                statusBarColor = it.toArgb()
                            }
                        )
                    }
                }
            }
        }
    }
}