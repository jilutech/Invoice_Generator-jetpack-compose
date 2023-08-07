package com.example.invoice.ui.home.invoice.manage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
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
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.home.invoice.components.InvoiceItemCard
import com.example.invoice.ui.home.invoice.components.InvoiceItemInput
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddInvoiceItem(viewModel: InvoicesViewModel, navController: NavController) {
    val context = LocalContext.current
    val spacing = MaterialTheme.spacing
    val invoice = viewModel.invoice.collectAsState()
    val createInvoice = viewModel.createInvoice.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (viewModel.canCreateInvoice()) {
                        viewModel.createOrUpdateInvoice()
                    } else {
                        context.toast(R.string.create_invoice_error)
                    }
                },
            ) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.empty))
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.medium)
            ) {
                Text(
                    text = stringResource(id = R.string.add_invoice_items),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = spacing.medium)
                )

                InvoiceItemInput(viewModel)

                Divider()

                Text(
                    text = stringResource(id = R.string.invoice),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = spacing.extraSmall, bottom = spacing.extraSmall)
                )

                Divider(modifier = Modifier.padding(bottom = spacing.medium))

                LazyColumn {
                    itemsIndexed(invoice.value.listOfItems) { index, item ->
                        InvoiceItemCard(viewModel, index, item)
                    }
                }
            }

            createInvoice.value?.let {
                when (it) {
                    is Resource.Failure -> {
                        context.toast(it.exception.message!!)
                    }
                    Resource.Loading -> {
                        FullScreenProgressbar()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(AppScreen.Invoices.route) {
                                popUpTo(AppScreen.Invoices.ManageInvoice.route) { inclusive = true }
                            }
                            context.toast(R.string.invoice_created)
                        }
                    }
                }
            }
        }
    )
}
