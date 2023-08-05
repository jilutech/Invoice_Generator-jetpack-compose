package com.example.invoice.ui.home.tax

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.invoice.R
import com.example.invoice.data.Resource
import com.example.invoice.ui.theme.spacing
import com.example.invoice.ui.utils.UserConfirmationDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTax(taxViewModel: TaxViewModel, navController: NavController) {
    val showDeleteConfirmation = remember { mutableStateOf(false) }

    val desc = taxViewModel.des.collectAsState()
    val value = taxViewModel.taxValue.collectAsState()

    val areInputsValid = taxViewModel.areInputValidate.collectAsState()
    val manageCustomerResult = taxViewModel.manageTax.collectAsState()

    val isUpdating = taxViewModel.isUpdating.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val spacing = MaterialTheme.spacing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = spacing.xLarge,
                end = spacing.xLarge,
                top = spacing.medium,
                bottom = spacing.medium
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.add_tax),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = spacing.large, bottom = spacing.large)
        )

        OutlinedTextField(
            value = desc.value,
            onValueChange = {
                taxViewModel.des.value = it
            },
            label = {
                if (desc.value.isEmpty()) {
                    Text(text = stringResource(id = R.string.desc_required))
                } else {
                    Text(text = stringResource(id = R.string.desc))
                }
            },
            modifier = Modifier
                .padding(bottom = spacing.medium)
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {

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
                    taxViewModel.validateInputs()
                    focusManager.moveFocus(FocusDirection.Next)
                },
            ),
            maxLines = 1,
            isError = desc.value.isEmpty()
        )

        OutlinedTextField(
            value = value.value,
            onValueChange = {
                taxViewModel.taxValue.value = it
            },
            label = {
                if (value.value.isEmpty()) {
                    Text(text = stringResource(id = R.string.tax_value_required))
                } else {
                    Text(text = stringResource(id = R.string.tax_value))
                }
            },
            modifier = Modifier
                .padding(bottom = spacing.medium)
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {

                    }
                },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    taxViewModel.validateInputs()
                },
            ),
            maxLines = 3,
            isError = value.value.isEmpty()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(spacing.xLarge)
        ) {
            manageCustomerResult.value?.let {
                when (it) {
                    is Resource.Failure -> {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                            taxViewModel.resetResource()
                        }
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

        Button(
            onClick = {
                if (isUpdating.value == null) {
                    taxViewModel.addTax()
                } else {
                    taxViewModel.updateTax()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing.xLarge, end = spacing.xLarge),
            enabled = areInputsValid.value
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
                taxViewModel.deleteCustomer()
            }
            showDeleteConfirmation.value = false
        }
    }
}