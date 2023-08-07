package com.example.invoice.ui.home.invoice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.invoice.R
import com.example.invoice.data.Resource
import com.example.invoice.sealed.AppScreen
import com.example.invoice.ui.home.invoice.components.InvoicesList
import com.example.invoice.ui.utils.EmptyScreen
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast
import com.google.accompanist.insets.ui.Scaffold
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Composable
fun InvoiceScreen(invoicesViewModel: InvoicesViewModel,navController: NavController){

    val context = LocalContext.current

    val invoice = invoicesViewModel.invoices.collectAsState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
                               FloatingActionButton(onClick = {
                                   invoicesViewModel.initNewInvoice()
                                   navController.navigate(AppScreen.Invoices.ManageInvoice.PickBusiness.route)
                               }
                               ) {
                                Icon(Icons.Filled.Add, stringResource(id = R.string.empty) )
                               }
        },
        content = {

            invoice.value.let {
                when(it){
                    is Resource.Failure -> {
                        context.toast(it.exception.message!!)
                    }
                    is Resource.Loading ->{
                        FullScreenProgressbar()
                    }
                    is Resource.Success -> {
                        if (it.result.isEmpty()){
                            EmptyScreen(title = stringResource(id = R.string.empty_invoice)) {
                            }
                        }else{
                            InvoicesList(
                                it.result,
                                navController,
                                invoicesViewModel
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    )
}