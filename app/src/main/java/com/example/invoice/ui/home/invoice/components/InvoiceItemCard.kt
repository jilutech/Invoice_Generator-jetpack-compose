package com.example.invoice.ui.home.invoice.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.invoice.R
import com.example.invoice.data.home.models.InvoiceItemModel
import com.example.invoice.ui.home.invoice.InvoicesViewModel
import com.example.invoice.ui.theme.spacing

@Composable
fun InvoiceItemCard(viewModel: InvoicesViewModel, index: Int, item: InvoiceItemModel) {
    val spacing = MaterialTheme.spacing
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.desc,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(0.5f)
                .padding(end = spacing.medium)
        )

        Text(
            text = item.qty.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(0.1f)
        )

        Text(
            text = item.price.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(0.2f)
                .padding(start = spacing.medium, end = spacing.extraSmall)
        )

        IconButton(
            onClick = {
                viewModel.initUpdateInvoiceItem(index)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
        }

        IconButton(
            onClick = {
                viewModel.deleteInvoiceItem(index)
            },
            modifier = Modifier.weight(0.1f)
        ) {
            Image(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(id = R.string.empty),
                modifier = Modifier.size(ButtonDefaults.IconSize),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
            )
        }
    }
}
