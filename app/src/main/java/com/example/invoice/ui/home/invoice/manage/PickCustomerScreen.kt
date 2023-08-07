package com.example.invoice.ui.home.invoice.manage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.invoice.R
import com.example.invoice.data.Resource
import com.example.invoice.data.home.repo.models.CustomerModel
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.home.customers.Customer
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.EmptyScreen
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast


@Composable
fun PickCustomerScreen(viewModel: InvoicesViewModel, navController: NavController) {
    val context = LocalContext.current
    val customers = viewModel.customers.collectAsState()

    customers.value?.let {
        when (it) {
            is Resource.Failure -> {
                context.toast(it.exception.message!!)
            }
            Resource.Loading -> {
                FullScreenProgressbar()
            }
            is Resource.Success -> {
                PickCustomer(it.result, viewModel, navController)
            }
        }
    }
}

@Composable
fun PickCustomer(customers: List<CustomerModel>, viewModel: InvoicesViewModel, navController: NavController) {
    val spacing = MaterialTheme.spacing
    if (customers.isEmpty()) {
        EmptyScreen(title = stringResource(id = R.string.empty_business)) { }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material.Text(
                text = stringResource(id = R.string.pick_a_customer),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(spacing.medium)
            )

            LazyColumn {
                items(customers) { item ->
                   Customer(
                       customer = item,
                       onClick = {
                           viewModel.setCustomer(item)
                           navController.navigate(AppScreen.Invoices.ManageInvoice.PickTax.route)
                       }
                   )
                }
            }
        }
    }
}
