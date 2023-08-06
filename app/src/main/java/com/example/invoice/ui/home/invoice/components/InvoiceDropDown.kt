package com.example.invoice.ui.home.invoice.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.invoice.sealed.InvoiceMenu
import com.example.invoice.ui.theme.spacing


@Composable
fun InvoiceDropDown(
    expanded : Boolean,
    isPaid : Boolean,
    onItemClick : (itemClick : InvoiceMenu) -> Unit,
    onDismiss : () -> Unit
){
    val spacing = MaterialTheme.spacing


    DropdownMenu(expanded = expanded, onDismissRequest = {
        onDismiss.invoke()
    }) {
        val invoiceList = listOf(
            if (isPaid) InvoiceMenu.MarkAsUnPaid else InvoiceMenu.MarkAsPaid,
            InvoiceMenu.Edit,
            InvoiceMenu.Delete
        )


        invoiceList.forEach{menu ->
            DropdownMenuItem(

                 text = {
                     Row {
                         Icon(
                             imageVector = menu.icon,
                             contentDescription = stringResource(menu.title),
                             modifier = Modifier.size(ButtonDefaults.IconSize)
                         )
                         Spacer(modifier = Modifier.size(spacing.small))
                         Text(text = stringResource(menu.title))
                     }
                 }

                , onClick = { onItemClick.invoke(menu)}

            )
        }
    }
}