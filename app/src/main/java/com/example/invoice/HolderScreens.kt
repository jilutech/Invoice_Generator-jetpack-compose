package com.example.invoice

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.SplashScreen
import com.example.invoice.ui.auth.LoginScreen
import com.example.invoice.ui.auth.SignupScreen
import com.example.invoice.ui.home.bussiness.BusinessScreen
import com.example.invoice.ui.home.bussiness.BusinessViewModel
import com.example.invoice.ui.home.bussiness.ManageMyBusiness
import com.example.invoice.ui.home.customers.Customers
import com.example.invoice.ui.home.customers.CustomersViewModel
import com.example.invoice.ui.home.customers.ManageCustomer
import com.example.invoice.ui.home.dashboard.DashboardScreen
import com.example.invoice.ui.home.invoice.InvoiceScreen
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.home.invoice.detail.InvoiceDetail
import com.example.invoice.ui.home.invoice.manage.AddInvoiceItem
import com.example.invoice.ui.home.invoice.manage.PickBusinessScreen
import com.example.invoice.ui.home.invoice.manage.PickCustomerScreen
import com.example.invoice.ui.home.invoice.manage.PickTaxScreen
import com.example.invoice.ui.home.tax.ManageTax
import com.example.invoice.ui.home.tax.TaxScreen
import com.example.invoice.ui.home.tax.TaxViewModel
import com.example.invoice.ui.utils.getViewModelInstance
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun HolderScreens(
    onStatusBarColorChange: (color: Color) -> Unit,
) {

    val destinations =  listOf(

        BottomNavItem(
            name = "DashBoard",
            route = AppScreen.Dashboard.route,
            icon = painterResource(id = com.example.invoice.R.drawable.baseline_space_dashboard_24)
        ),
        BottomNavItem(
            name = "Businesses",
            route = AppScreen.MyBusinesses.route,
            icon = painterResource(id = com.example.invoice.R.drawable.ic_my_businesses)
        ),
        BottomNavItem(
            name = "Customers",
            route = AppScreen.Customers.route,
            icon = painterResource(id = com.example.invoice.R.drawable.ic_customers)
        ),
        BottomNavItem(
            name = "Invoice",
            route = AppScreen.Invoices.route,
            icon = painterResource(id = com.example.invoice.R.drawable.ic_invoices)
        ),
        BottomNavItem(
            name = "Tax",
            route = AppScreen.Taxes.route,
            icon = painterResource(id = com.example.invoice.R.drawable.ic_taxes)
        )
    )
    /** Our navigation controller that the MainActivity provides */
    val navController = LocalNavHost.current

    /** The current active navigation route */
    val currentRouteAsState = getActiveRoutes(navController = navController)


    /** The main app's scaffold state */
    val scaffoldState = rememberScaffoldState()

    /** The coroutine scope */
    val scope = rememberCoroutineScope()




        ScaffoldSection(
            navController = navController,
            scaffoldState = scaffoldState,
            onStatusBarColorChange = onStatusBarColorChange,
            bottomNavigationContent = {
                if (
                    currentRouteAsState in destinations.map { it.route }
                ) {
                    AppBottomNav(
                        activeRoute = currentRouteAsState,
                        bottomNavDestinations = destinations,
                        onActiveRouteChange = {
                            if (it != currentRouteAsState) {
                                /** We should navigate to that new route */
                                /** We should navigate to that new route */
                                navController.navigate(it) {
                                    popUpTo(AppScreen.Dashboard.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            },
            onSplashFinished = { nextDestination ->
                navController.navigate(nextDestination.route) {
                    popUpTo(AppScreen.Splash.route) {
                        inclusive = true
                    }
                }
            },
            onBackRequested = {
                navController.popBackStack()
            },
            onNavigationRequested = { route, removePreviousRoute ->
                if (removePreviousRoute) {
                    navController.popBackStack()
                }
                navController.navigate(route)
            },
//            onUserNotAuthorized = { removeCurrentRoute ->
//                if (removeCurrentRoute) {
//                    controller.popBackStack()
//                }
//                controller.navigate(Screen.Login.route)
//            }
        )
}

@Composable
fun ScaffoldSection(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onStatusBarColorChange: (color: Color) -> Unit,
    onSplashFinished: (nextDestination: AppScreen) -> Unit,
    onNavigationRequested: (route: String, removePreviousRoute: Boolean) -> Unit,
    onBackRequested: () -> Unit,
//  onUserNotAuthorized: (removeCurrentRoute: Boolean) -> Unit,
    bottomNavigationContent: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
                .background(MaterialTheme.colors.background)
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
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
                    DashboardScreen(hiltViewModel())
                }
                composable(AppScreen.Customers.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    Customers(customersViewModel = hiltViewModel(), navController =navController )
                }
                composable(AppScreen.Customers.ManageCustomer.route) {
                    val vm = navController.getViewModelInstance<CustomersViewModel>(it, AppScreen.Customers.route)
                    ManageCustomer(vm, navController)

                }
                composable(AppScreen.MyBusinesses.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    BusinessScreen(businessViewModel = hiltViewModel(), navController =navController )
                }
                composable(AppScreen.MyBusinesses.ManageMyBusiness.route){
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val vm = navController.getViewModelInstance<BusinessViewModel>(it, AppScreen.MyBusinesses.route)
                    ManageMyBusiness(vm,navController)
                }
                composable(AppScreen.Taxes.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    TaxScreen(hiltViewModel(), navController = navController)
                }
                composable(AppScreen.Taxes.ManageTaxes.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val vm = navController.getViewModelInstance<TaxViewModel>(it, AppScreen.Taxes.route)
                    ManageTax(vm,navController)
                }
                composable(AppScreen.Invoices.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    InvoiceScreen(vm,navController)
                }
                composable(AppScreen.Invoices.ManageInvoice.PickBusiness.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    PickBusinessScreen(vm, navController)
                }

                composable(AppScreen.Invoices.ManageInvoice.PickCustomer.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    PickCustomerScreen(vm, navController)
                }

                composable(AppScreen.Invoices.ManageInvoice.PickTax.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    PickTaxScreen(vm, navController)
                }

                composable(AppScreen.Invoices.ManageInvoice.AddItems.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    AddInvoiceItem(vm, navController)
                }
                composable(AppScreen.Invoices.ManageInvoice.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    AddInvoiceItem(vm, navController)
                }
                composable(AppScreen.Invoices.InvoiceDetail.route) {
                    val vm = navController.getViewModelInstance<InvoicesViewModel>(it, AppScreen.Invoices.route)
                    InvoiceDetail(vm, rememberNavController())
                }
            }
            /** Now we lay down our bottom navigation component */

            bottomNavigationContent()
        }
    }
}

//@Composable
//fun AppBottomNav(
//    activeRoute: String,
//    bottomNavDestinations: List<AppScreen>,
//    onActiveRouteChange: (route: String) -> Unit,
//){
//
//    NavigationBar(
//        modifier = Modifier.fillMaxWidth(),
//        containerColor = Color.White,
//        tonalElevation = 5.dp
//    ) {
//        bottomNavDestinations.forEach {
//            val isActive = activeRoute.equals(other = it.route, ignoreCase = true)
//            NavigationBarItem(
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color.Blue,
//                    unselectedIconColor = Color.Black
//                ),
//                icon = {
//                    androidx.compose.material.Icon(
//                        painter = painterResource(id = it.icon!!),
//                        contentDescription = it.title.toString(),
//                        modifier = Modifier.size(20.dp)
//                    )
//                },
//                selected = isActive,
//                onClick = { if (!isActive) {
//                    onActiveRouteChange(it.route)
//                }
//                          },
//                label = {
//                    Text(
//                        text = it.title.toString(),
//                        textAlign = TextAlign.Center,
//                        fontSize = 10.sp
//                    )
//                }
//            )
//        }
//    }
//
//}

@Composable
fun AppBottomNav(
    activeRoute: String,
    bottomNavDestinations: List<BottomNavItem>,
    onActiveRouteChange: (route: String) -> Unit,
){

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        tonalElevation = 5.dp
    ) {
//        bottomNavDestinations.forEach {
//            val isActive = activeRoute.equals(other = it.route, ignoreCase = true)
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            tonalElevation = 5.dp
        ) {
            bottomNavDestinations.forEach {
                val isActive = activeRoute.equals(other = it.route, ignoreCase = true)
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Blue,
                        unselectedIconColor = Color.Black
                    ),
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.name,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    selected = isActive,

                     onClick = { if (!isActive) {
                    onActiveRouteChange(it.route)
                       }
                          },
                    label = {
                        Text(
                            text = it.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                )
            }
        }



        //            NavigationBarItem(
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color.Blue,
//                    unselectedIconColor = Color.Black
//                ),
//                icon = {
//                    androidx.compose.material.Icon(
//                        painter = painterResource(id = it.icon!!),
//                        contentDescription = it.title.toString(),
//                        modifier = Modifier.size(20.dp)
//                    )
//                },
//                selected = isActive,
//                onClick = { if (!isActive) {
//                    onActiveRouteChange(it.route)
//                }
//                          },
//                label = {
//                    Text(
//                        text = it.title.toString(),
//                        textAlign = TextAlign.Center,
//                        fontSize = 10.sp
//                    )
//                }
//            )
//        }
    }

}
@Composable
fun getActiveRoutes(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: "splash"
}
