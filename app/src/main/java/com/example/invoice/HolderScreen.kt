package com.example.invoice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.SplashScreen
import com.example.invoice.ui.auth.LoginScreen
import com.example.invoice.ui.auth.SignupScreen
import com.example.invoice.ui.home.dashboard.DashboardScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HolderScreen(
    onStatusBarColorChange: (color: Color) -> Unit,
) {

    val controller = LocalNavHost.current


    // A surface container using the 'background' color from the theme
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navControllerHost = controller, items = listOf(

                BottomNavItem(
                    name = "DashBoard",
                    route = AppScreen.Dashboard.route,
                    icon = Icons.Default.Home
                ),
                BottomNavItem(
                    name = "Businesses",
                    route = AppScreen.MyBusinesses.route,
                    icon = Icons.Default.Star
                ),
                BottomNavItem(
                    name = "Customers",
                    route = AppScreen.Customers.route,
                    icon = Icons.Default.Favorite
                ),
            ),
             navController = controller, onItemClick = {
                     /** We should navigate to that new route */
                        controller.navigate(it.route) {
//                            popUpTo(AppScreen.Dashboard.route) {
//                                saveState = true
//                            }
                            launchSingleTop = true
                            restoreState = true
                        }
            })
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Navigation(navController = controller,onStatusBarColorChange)
    }
}

@Composable
fun Navigation(navController: NavHostController, onStatusBarColorChange: (color: Color) -> Unit){
    NavHost(
        navController = navController,
        startDestination = AppScreen.Splash.route
    ) {
        composable(AppScreen.Splash.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            SplashScreen()
        }

        composable(AppScreen.Auth.Login.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            LoginScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Auth.Signup.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            SignupScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Dashboard.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            DashboardScreen()
        }
        composable(AppScreen.Taxes.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
//                    SearchScreen()
        }
        composable(AppScreen.Customers.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            SignupScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Invoices.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
//                    NotificationScreen()
        }
        composable(AppScreen.MyBusinesses.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
            LoginScreen(hiltViewModel(), navController)
        }
        composable(AppScreen.Logout.route) {
            onStatusBarColorChange(MaterialTheme.colors.background)
//                    ChatsScreen()
        }
    }
}


@Composable
fun BottomNavigationBar(
    navControllerHost: NavHostController,
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
//    Divider()
    val currentDestinationAsState = getActiveRoute(navController = navControllerHost)

    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
            items.forEach { item ->
                if (currentDestinationAsState in items.map { it.route }) {
                    val selected = item.route == backStackEntry.value?.destination?.route
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = Color.Black
                        ),
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.name
                            )
                        },
                        selected = selected,
                        onClick = {onItemClick(item)},
                        label = {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    )
                }
            }
    }
}

/**
 * A function that is used to get the active route in our Navigation Graph , should return the splash route if it's null
 */
@Composable
fun getActiveRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: "splash"
}