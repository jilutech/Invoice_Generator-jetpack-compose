package com.example.invoice.ui.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.invoice.ui.AppScreen
import com.example.invoice.ui.home.customers.ManageCustomer

@Composable
fun AuthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Auth.Login.route
    ) {
        composable(AppScreen.Auth.Login.route) {
            LoginScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Auth.Signup.route) {
            SignupScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Customers.ManageCustomer.route) {
            ManageCustomer(hiltViewModel(), navController)
        }
        composable(AppScreen.Customers.route) {
            SignupScreen(hiltViewModel(), navController)
        }

    }
}