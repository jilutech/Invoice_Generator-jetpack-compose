package com.example.invoice.ui.home.bussiness

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(businessViewModel: BusinessViewModel,navController:NavController){

    val context = LocalContext.current
    val canAddBusiness = businessViewModel.canAddBusiness.collectAsState()
    val myBusinessHolders = businessViewModel.getMyBusinessModel.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton ={
            FloatingActionButton(onClick = {
                    if (canAddBusiness.value) {
                        businessViewModel.setUpdating(null)
                        businessViewModel.validatesInput()
                        navController.navigate(AppScreen.MyBusinesses.ManageMyBusiness.route)
                    }else {
                        context.toast(R.string.maximum_business_reached)
                    }
                  },
                ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.empty) )

            }
        },
        content = {

            myBusinessHolders.value?.let {
                when(it){
                    is Resource.Loading -> {
                        FullScreenProgressbar()
                    }
                    is Resource.Success -> {
                        if (it.result.isEmpty()){
                            EmptyScreen(title = stringResource(id =R.string.empty_business)) { }
                        }else{
                            LazyColumn {
                                items(it.result){  item ->

                                    BusinessCard(
                                        businessModel = item,
                                        onClick = {
                                           businessViewModel.setUpdating(item)
                                           navController.navigate(AppScreen.MyBusinesses.ManageMyBusiness.route)
                                        }
                                      )
                                }
                            }
                        }
                    }
                    is Resource.Failure -> {
                        context.toast(it.exception.message!!)
                    }
                }
            }



        }
        )
}