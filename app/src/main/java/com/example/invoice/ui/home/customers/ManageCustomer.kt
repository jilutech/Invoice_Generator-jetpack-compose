package com.example.invoice.ui.home.customers

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.invoice.R
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.CustomButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageCustomer(customersViewModel: CustomersViewModel?,navController: NavController){
      val showDeleteConfirmation = remember {
          mutableStateOf(true)
      }

      val name = customersViewModel?.name?.collectAsState()
      val address = customersViewModel?.address?.collectAsState()
      val phone = customersViewModel?.phone?.collectAsState()
      val email = customersViewModel?.email?.collectAsState()


      val areInputValidate = customersViewModel?.areInputValid?.collectAsState()
      val manageCustomer = customersViewModel?.manageCustomerResult?.collectAsState()
      val isUpdating = customersViewModel?.isUpdating?.collectAsState()


       val coroutineScope = rememberCoroutineScope()
       val focusManager = LocalFocusManager.current
       val bringIntoViewRequester = BringIntoViewRequester()
       val context = LocalContext.current

       val spacing = MaterialTheme.spacing

    
       Column(modifier = Modifier
           .padding(spacing.medium.times(1.5f)),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center


       ) {
           val fullWidthModifier =
               Modifier
                   .fillMaxWidth()
                   .padding(8.dp)
           Text(text = stringResource(id = R.string.add_customer),
               style = MaterialTheme.typography.headlineMedium,
               fontFamily = FontFamily.SansSerif,
               color = MaterialTheme.colorScheme.onPrimaryContainer,
               modifier = Modifier.padding(top = spacing.large)
           )

           OutlinedTextField(
               maxLines = 1,
               modifier = fullWidthModifier,
               value = "",
               onValueChange = { newValue ->
//                  newValue customersViewModel.name.value =newValue
               },
               label = {
//                   if (name?.value!!.isEmpty()) {
//                       Text(text = stringResource(id = R.string.name_required))
//                   } else {
                       Text(text = stringResource(id = R.string.name))
//                   }
               },
               placeholder = {Text(text = stringResource(id = R.string.name)) },

               colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                   textColor = Color.Black,
                   backgroundColor = Color.White,
                   focusedIndicatorColor = Color(0xff1976D2)
               )
           )
           OutlinedTextField(
               modifier = fullWidthModifier.height(110.dp),
               value = "",
               onValueChange = { newValue ->
//                  newValue customersViewModel.name.value =newValue
               },
               label = {
//                   if (name?.value!!.isEmpty()) {
//                       Text(text = stringResource(id = R.string.name_required))
//                   } else {
                   Text(text = stringResource(id = R.string.address))
//                   }
               },
               placeholder = {Text(text = stringResource(id = R.string.name)) },

               colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                   textColor = Color.Black,
                   backgroundColor = Color.White,
                   focusedIndicatorColor = Color(0xff1976D2)
               ),
               maxLines = 3
           )
           OutlinedTextField(
               maxLines = 1,
               modifier = fullWidthModifier,
               value = "",
               onValueChange = { newValue ->
//                  newValue customersViewModel.name.value =newValue
               },
               label = {
//                   if (name?.value!!.isEmpty()) {
//                       Text(text = stringResource(id = R.string.name_required))
//                   } else {
                   Text(text = stringResource(id = R.string.phone))
//                   }
               },
               placeholder = {Text(text = stringResource(id = R.string.name)) },

               colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                   textColor = Color.Black,
                   backgroundColor = Color.White,
                   focusedIndicatorColor = Color(0xff1976D2)
               ),

               keyboardOptions = KeyboardOptions(
               capitalization = KeyboardCapitalization.None,
               autoCorrect = false,
               keyboardType = KeyboardType.Phone,
               imeAction = ImeAction.Next
           )
           )
           OutlinedTextField(
               maxLines = 1,
               modifier = fullWidthModifier,
               value = "",
               onValueChange = { newValue ->
//                  newValue customersViewModel.name.value =newValue
               },
               label = {
//                   if (name?.value!!.isEmpty()) {
//                       Text(text = stringResource(id = R.string.name_required))
//                   } else {
                   Text(text = stringResource(id = R.string.email))
//                   }
               },
               placeholder = {Text(text = stringResource(id = R.string.name)) },

               colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                   textColor = Color.Black,
                   backgroundColor = Color.White,
                   focusedIndicatorColor = Color(0xff1976D2)
               )
           )
           Button(
               onClick = {
//                   if (isUpdating.value == null) {
//                       viewModel.addCustomer()
//                   } else {
//                       viewModel.updateCustomer()
//                   }
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = spacing.xLarge, end = spacing.xLarge)
                   .bringIntoViewRequester(bringIntoViewRequester),
//               enabled = areInputsValid.value
           ) {
               Text(
                   text = stringResource(id = R.string.add),
                   style = MaterialTheme.typography.titleMedium
               )
           }
           
       }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun dateTimePickerColors() = TextFieldDefaults.textFieldColors(
    errorIndicatorColor = Color.Red,
    containerColor = Color.White
)


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ManagePrev(){
    InvoiceTheme() {
        ManageCustomer(null , navController = rememberNavController() )
    }
}