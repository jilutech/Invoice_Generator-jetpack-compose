package com.example.invoice.ui.home.customers

 import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
 import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
 import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
 import androidx.compose.ui.unit.dp
 import androidx.navigation.NavController
 import com.example.invoice.R
import com.example.invoice.data.Resource
 import com.example.invoice.ui.theme.spacing
 import com.example.invoice.ui.utils.UserConfirmationDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ManageCustomer(customersViewModel: CustomersViewModel,navController: NavController){
      val showDeleteConfirmation = remember {
          mutableStateOf(false)
      }

      val name = customersViewModel.name.collectAsState()
      val address = customersViewModel.address.collectAsState()
      val phone = customersViewModel.phone.collectAsState()
      val email = customersViewModel.email.collectAsState()


      val areInputValidate = customersViewModel.areInputValid.collectAsState()
      val manageCustomer = customersViewModel.manageCustomerResult.collectAsState()
      val isUpdating = customersViewModel.isUpdating.collectAsState()


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
           Text(text = stringResource(id = R.string.add_customer),
               style = MaterialTheme.typography.headlineMedium,
               fontFamily = FontFamily.SansSerif,
               color = MaterialTheme.colorScheme.onPrimaryContainer,
               modifier = Modifier.padding(top = spacing.large)
           )

           OutlinedTextField(
               value = name.value,
               onValueChange = {
                   customersViewModel.name.value = it
               },
               label = {
                   if (name.value.isEmpty()) {
                       Text(text = stringResource(id = R.string.name_required))
                   } else {
                       Text(text = stringResource(id = R.string.name))
                   }
               },
               modifier = Modifier
                   .padding(bottom = spacing.medium)
                   .fillMaxWidth()
                   .onFocusEvent {
//                       if (it.isFocused) {
//                           coroutineScope.launch {
//                               bringIntoViewRequester.bringIntoView()
//                           }
//                       }
                   },
               keyboardOptions = KeyboardOptions(
                   capitalization = KeyboardCapitalization.None,
                   autoCorrect = false,
                   keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Next
               ),
               keyboardActions = KeyboardActions(
                   onNext = {
                       customersViewModel.validateInputs()
                       focusManager.moveFocus(FocusDirection.Next)
                   },
               ),
               maxLines = 1,
               isError = name.value.isEmpty()
           )
           OutlinedTextField(
               value = address.value,
               onValueChange = {
                   customersViewModel.address.value = it
               },
               label = {
                   if (address.value.isEmpty()) {
                       Text(text = stringResource(id = R.string.address_required))
                   } else {
                       Text(text = stringResource(id = R.string.address))
                   }
               },
               modifier = Modifier
                   .padding(bottom = spacing.medium)
                   .fillMaxWidth()
                   .height(110.dp)
                   .onFocusEvent {
                       if (it.isFocused) {
                           coroutineScope.launch {
                               bringIntoViewRequester.bringIntoView()
                           }
                       }
                   },
               keyboardOptions = KeyboardOptions(
                   capitalization = KeyboardCapitalization.None,
                   autoCorrect = false,
                   keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Next
               ),
               keyboardActions = KeyboardActions(
                   onNext = {
                       customersViewModel.validateInputs()
                       focusManager.moveFocus(FocusDirection.Next)
                   },
               ),
               maxLines = 3,
               isError = address.value.isEmpty()
           )
           OutlinedTextField(
               value = phone.value,
               onValueChange = {
                   customersViewModel.phone.value = it
               },
               label = {
                   if (phone.value.isEmpty()) {
                       Text(text = stringResource(id = R.string.phone_required))
                   } else {
                       Text(text = stringResource(id = R.string.phone))
                   }
               },
               modifier = Modifier
                   .padding(bottom = spacing.medium)
                   .fillMaxWidth()
                   .onFocusEvent {
                       if (it.isFocused) {
                           coroutineScope.launch {
                               bringIntoViewRequester.bringIntoView()
                           }
                       }
                   },
               keyboardOptions = KeyboardOptions(
                   capitalization = KeyboardCapitalization.None,
                   autoCorrect = false,
                   keyboardType = KeyboardType.Phone,
                   imeAction = ImeAction.Next
               ),
               keyboardActions = KeyboardActions(
                   onNext = {
                       customersViewModel.validateInputs()
                       focusManager.moveFocus(FocusDirection.Next)
                   },
               ),
               maxLines = 1,
               isError = phone.value.isEmpty()
           )
           OutlinedTextField(

               value = email.value,
               onValueChange = {
                   customersViewModel.email.value = it
               },
               label = {
                   if (phone.value.isEmpty()) {
                       Text(text = stringResource(id = R.string.email_required))
                   } else {
                       Text(text = stringResource(id = R.string.email))
                   }
               },
               modifier = Modifier
                   .padding(bottom = spacing.medium)
                   .fillMaxWidth()
                   .onFocusEvent {
                       if (it.isFocused) {
                           coroutineScope.launch {
                               bringIntoViewRequester.bringIntoView()
                           }
                       }
                   },
               keyboardOptions = KeyboardOptions(
                   capitalization = KeyboardCapitalization.None,
                   autoCorrect = false,
                   keyboardType = KeyboardType.Email,
                   imeAction = ImeAction.Done
               ),
               keyboardActions = KeyboardActions(
                   onDone = {
                       focusManager.clearFocus()
                       customersViewModel.validateInputs()
                   }
               ),
               maxLines = 1,
               isError = email.value.isEmpty()
           )

           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .height(spacing.xLarge)
           ) {
               manageCustomer.value?.let {
                   when (it) {
                       is Resource.Failure -> {
                           Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                       }
                       is Resource.Success -> {
                           LaunchedEffect(Unit) {
                               navController.popBackStack()
                               customersViewModel.resetResource()
                           }
                       }
                       Resource.Loading -> {
                           androidx.compose.material3.CircularProgressIndicator(
                               modifier = Modifier.align(
                                   Alignment.Center
                               )
                           )
                       }
                   }
               }
           }
           Button(
               onClick = {
                   if (isUpdating.value == null) {
                       customersViewModel.addCustomer()
                   } else {
                       customersViewModel.updateCustomer()
                   }
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = spacing.xLarge, end = spacing.xLarge)
                   .bringIntoViewRequester(bringIntoViewRequester),
               enabled = areInputValidate.value
           ) {
               Text(
                   text = if (isUpdating.value == null) stringResource(id = R.string.add) else stringResource(id = R.string.update),
                   style = MaterialTheme.typography.titleMedium
               )
           }

           if (isUpdating.value != null) {
               Button(
                   onClick = {
                       showDeleteConfirmation.value = true
                   },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(start = spacing.xLarge, top = spacing.medium, end = spacing.xLarge),
                   colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
               ) {
                   Text(
                       text = stringResource(id = R.string.delete),
                       style = MaterialTheme.typography.titleMedium,
                       color = MaterialTheme.colorScheme.onError
                   )
               }
           }
       }

    if (showDeleteConfirmation.value) {
        UserConfirmationDialog { confirmation ->
            if (confirmation) {
                customersViewModel.delete()
            }
            showDeleteConfirmation.value = false
        }
    }

}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
// fun dateTimePickerColors() = TextFieldDefaults.textFieldColors(
//    errorIndicatorColor = Color.Red,
//    containerColor = Color.White
//)


//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Composable
//fun ManagePrev(){
//    InvoiceTheme() {
//        ManageCustomer(null , navController = rememberNavController() )
//    }
//}