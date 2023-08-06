package com.example.invoice.ui.home.invoice.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.invoice.data.home.models.InvoiceModel
import com.example.invoice.sealed.AppScreen
import com.example.invoice.sealed.InvoiceMenu
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.utils.UserConfirmationDialog

@Composable
fun InvoicesList(invoiceList : List<InvoiceModel>,navController: NavController,viewModel: InvoicesViewModel){
    val invoiceDeleteConfirmation = remember { mutableStateOf<String?>(null) }


    LazyColumn{
        items(invoiceList)
        { item ->
        
            InvoiceCard(invoiceModel = item,
                onClick = {
                    viewModel.setCurrentInvoice(item)
                    navController.navigate(AppScreen.Invoices.InvoiceDetail.route)
                },
                onMenuClick = {
                    when (it) {
                        InvoiceMenu.Delete -> {
                            invoiceDeleteConfirmation.value = item.id
                        }
                        InvoiceMenu.Edit -> {
                            viewModel.initInvoiceUpdate(item)
                            navController.navigate(AppScreen.Invoices.ManageInvoice.route)
                        }
                        InvoiceMenu.MarkAsPaid -> {
                            viewModel.updatePaidState(item.id, true)
                        }
                        InvoiceMenu.MarkAsUnPaid -> {
                            viewModel.updatePaidState(item.id, false)
                        }
                    }
                }

            )

        }
    }

    if (invoiceDeleteConfirmation.value != null) {
        UserConfirmationDialog { confirmation ->
            if (confirmation) {
                viewModel.deleteInvoice(invoiceDeleteConfirmation.value!!)
            }
            invoiceDeleteConfirmation.value = null
        }
    }

}