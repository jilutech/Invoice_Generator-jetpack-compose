package com.example.invoice.ui.home.customers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.invoice.R
import com.example.invoice.data.Resource
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.utils.EmptyScreen
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Customers(customersViewModel: CustomersViewModel,navController: NavController) {
    var context = LocalContext.current
    var customers = customersViewModel.customer.collectAsState()


    Scaffold(modifier = Modifier.fillMaxSize(),
             floatingActionButton = {
                 FloatingActionButton(
                     onClick = {
                         customersViewModel.setUpdating(null)
                         customersViewModel.validateInputs()
                         navController.navigate(AppScreen.Customers.ManageCustomer.route)
                     })
                 {
                     androidx.compose.material3.Icon(
                         Icons.Filled.Add,
                         stringResource(id = R.string.empty)
                     )

                 }
             },
           content = {
            customers.value?.let {
                when (it) {
                    is Resource.Failure -> {
                        context.toast(it.exception.message!!)
                        Log.e("Failure","1")
                    }
                    Resource.Loading -> {
                        FullScreenProgressbar()
                        Log.e("loading","2")
                    }
                    is Resource.Success -> {
                        if (it.result.isEmpty()) {
                            EmptyScreen(title = stringResource(id = R.string.empty_customer)) {
                                Log.e("isEmpty","3")

                            }
                        } else {
                            Log.e("notisEmpty","4")
                            LazyColumn {
                                items(it.result) { item ->
                                    Log.e("result","5")
                                    Customer(
                                        customer = item,
                                        onClick = {
                                            customersViewModel.setUpdating(item)
                                            navController.navigate(AppScreen.Customers.ManageCustomer.route)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    )

}