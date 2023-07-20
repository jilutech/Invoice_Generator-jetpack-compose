package com.example.invoice.ui.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.invoice.ui.BaseActivity
import com.example.invoice.ui.theme.InvoiceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvoiceTheme {
                AuthNavHost(rememberNavController())
            }
        }
    }
}