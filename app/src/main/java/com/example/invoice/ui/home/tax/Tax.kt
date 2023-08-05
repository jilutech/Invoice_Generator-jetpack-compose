package com.example.invoice.ui.home.tax

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.invoice.data.home.models.TaxModel
import com.example.invoice.ui.theme.spacing

@Composable
fun Tax(taxModel: TaxModel,onClick : () -> Unit) {
    val spacing = MaterialTheme.spacing

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(spacing.medium)
        .wrapContentHeight()
        .clickable {
            onClick()
        }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(spacing.medium)
        ) {
            Text(
                text = taxModel.desc,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = taxModel.taxLabel,
                style = MaterialTheme.typography.titleMedium
            )
            
        }
        
    }
}
