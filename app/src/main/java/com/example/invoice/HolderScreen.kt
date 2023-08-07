package com.example.invoice

import android.annotation.SuppressLint
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                    icon = painterResource(id = R.drawable.baseline_space_dashboard_24)
                ),
                BottomNavItem(
                    name = "Businesses",
                    route = AppScreen.MyBusinesses.route,
                    icon = painterResource(id = R.drawable.ic_my_businesses)
                ),
                BottomNavItem(
                    name = "Customers",
                    route = AppScreen.Customers.route,
                    icon = painterResource(id = R.drawable.ic_customers)
                ),
                BottomNavItem(
                    name = "Invoice",
                    route = AppScreen.Invoices.route,
                    icon = painterResource(id = R.drawable.ic_invoices)
                ),
                BottomNavItem(
                    name = "Tax",
                    route = AppScreen.Taxes.route,
                    icon = painterResource(id = R.drawable.ic_taxes)
                )
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
    ) {innerpadding ->
        Navigation(navController = controller,onStatusBarColorChange,innerpadding)
    }
}

@Composable
fun Navigation(
    navController: NavHostController,
    onStatusBarColorChange: (color: Color) -> Unit,
    innerpadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = AppScreen.Splash.route,
        modifier = Modifier.padding(innerpadding)
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
            InvoiceScreen(hiltViewModel(),navController)
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
        composable(AppScreen.Invoices.InvoiceDetail.route) {
            InvoiceDetail(hiltViewModel(), rememberNavController())
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
                                contentDescription = item.name,
                                modifier = Modifier.size(20.dp)
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