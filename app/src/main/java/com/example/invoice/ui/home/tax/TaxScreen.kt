package com.example.invoice.ui.home.tax

import android.annotation.SuppressLint
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
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.EmptyScreen
import com.example.invoice.ui.utils.FullScreenProgressbar
import com.example.invoice.ui.utils.toast

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaxScreen(taxViewModel: TaxViewModel,navController: NavController){

    val context = LocalContext.current
    val taxList = taxViewModel.taxList.collectAsState()
    val spacing = MaterialTheme.spacing
     Scaffold(
         modifier = Modifier.fillMaxSize(),
         floatingActionButton = {
             FloatingActionButton(
                 onClick = {
                 taxViewModel.setUpdating(null)
                 taxViewModel.validateInputs()
                 navController.navigate(AppScreen.Taxes.ManageTaxes.route)
             }
             ) {
                 Icon(Icons.Filled.Add, stringResource(id = R.string.empty))
             }
         },
         content = {
              taxList.value?.let {
                  when(it){
                      is Resource.Failure -> {
                          context.toast(it.exception.message!!)
                      }
                      is Resource.Success -> {
                          if (it.result.isEmpty()){
                              EmptyScreen(title = stringResource(id = R.string.empty_taxes)) {

                              }
                          }else{
                              LazyColumn{
                                 items(it.result){ item ->
                                     Tax(taxModel = item,
                                         onClick = {
                                         taxViewModel.setUpdating(item)
                                         navController.navigate(AppScreen.Taxes.ManageTaxes.route)
                                     }
                                     )
                                 }
                              }
                          }
                      }
                      is Resource.Loading -> {
                          FullScreenProgressbar()
                      }
                  }
              }
         }
     )
}