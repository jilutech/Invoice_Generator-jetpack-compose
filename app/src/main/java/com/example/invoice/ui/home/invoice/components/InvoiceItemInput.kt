package com.example.invoice.ui.home.invoice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.example.invoice.R
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceItemInput(viewModel: InvoicesViewModel) {
    val focusManager = LocalFocusManager.current
    val spacing = MaterialTheme.spacing

    val desc = viewModel.desc.collectAsState()
    val qty = viewModel.qty.collectAsState()
    val price = viewModel.price.collectAsState()
    val areInputsValid = viewModel.areInputsValid.collectAsState()
    val isUpdatingInvoiceItem = viewModel.isUpdatingInvoiceItem.collectAsState()

    Column(
        modifier = Modifier.wrapContentHeight()
    ) {
        Row {
            OutlinedTextField(
                value = desc.value,
                onValueChange = {
                    viewModel.desc.value = it
                },
                label = { Text(text = stringResource(id = R.string.desc)) },
                modifier = Modifier
                    .weight(2f)
                    .padding(end = spacing.small),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = desc.value.isEmpty()
            )

            OutlinedTextField(
                value = qty.value,
                onValueChange = {
                    viewModel.qty.value = it
                },
                label = { Text(text = stringResource(id = R.string.qty)) },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = qty.value.toDoubleOrNull() == null
            )

            OutlinedTextField(
                value = price.value,
                onValueChange = {
                    viewModel.price.value = it
                },
                label = { Text(text = stringResource(id = R.string.price)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = spacing.small),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.validateInputs()
                    },
                ),
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colorScheme.onSurface),
                isError = price.value.toDoubleOrNull() == null
            )
        }
        TextButton(
            onClick = {
                if (isUpdatingInvoiceItem.value != -1) {
                    viewModel.updateInvoiceItem()
                } else {
                    viewModel.addInvoiceItem()
                }
                focusManager.clearFocus()
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = spacing.medium),
            enabled = areInputsValid.value,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = if (isUpdatingInvoiceItem.value != -1) stringResource(id = R.string.update) else stringResource(id = R.string.add),
                style = MaterialTheme.typography.titleMedium,
                color = if (areInputsValid.value) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        }
    }
}
