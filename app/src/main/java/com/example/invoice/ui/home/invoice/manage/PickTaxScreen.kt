package com.example.invoice.ui.home.invoice.manage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.invoice.R
import com.example.invoice.data.Resource
import com.example.invoice.data.home.models.TaxModel
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.home.invoice.components.InvoiceItemCard
import com.example.invoice.ui.home.invoice.components.InvoiceItemInput
import com.example.invoice.ui.home.tax.Tax
import com.example.invoice.ui.home.tax.TaxScreen
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.EmptyScreen
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast


@Composable
fun PickTaxScreen(viewModel: InvoicesViewModel, navController: NavController) {
    val context = LocalContext.current
    val taxes = viewModel.taxes.collectAsState()

    taxes.value?.let {
        when (it) {
            is Resource.Failure -> {
                context.toast(it.exception.message!!)
            }
            Resource.Loading -> {
                FullScreenProgressbar()
            }
            is Resource.Success -> {
                PickTax(it.result, viewModel, navController)
            }
        }
    }
}

@Composable
fun PickTax(taxes: List<TaxModel>, viewModel: InvoicesViewModel, navController: NavController) {
    val spacing = MaterialTheme.spacing
    if (taxes.isEmpty()) {
        EmptyScreen(title = stringResource(id = R.string.empty_taxes)) { }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material.Text(
                text = stringResource(id = R.string.pick_a_tax),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(spacing.medium)
            )

            LazyColumn {
                items(taxes) { item ->
                    Tax(
                        taxModel = item,
                        onClick = {
                            viewModel.setTax(item)
                            navController.navigate(AppScreen.Invoices.ManageInvoice.AddItems.route)
                        }
                    )
                }
            }
        }
    }
}
