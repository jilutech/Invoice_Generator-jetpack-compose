package com.example.invoice.ui.home.invoice.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.invoice.R
import com.example.invoice.data.home.models.InvoiceModel
import com.example.invoice.data.utils.toDateString
import com.example.invoice.sealed.InvoiceMenu
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing

@Composable
fun InvoiceCard(invoiceModel: InvoiceModel,onClick : () -> Unit,onMenuClick : (menu : InvoiceMenu) -> Unit){
    val context = LocalContext.current
    val spacing = MaterialTheme.spacing


    val isMenuExpanded = remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .clip(RoundedCornerShape(spacing.medium))
        .fillMaxWidth()
        .wrapContentHeight()
        .clickable { onClick() }
        .padding(spacing.medium)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.medium)
        )
        {

            Row(verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                ) {

                Text(
                    text = context.toDateString(invoiceModel.createdAt),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(0.9f)
                )
                Box(modifier = Modifier
                    .weight(0.1f)
                    .clickable {
                        isMenuExpanded.value = true
                    }
                ){
                    Image(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(id = R.string.empty),
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                    InvoiceDropDown(
                        expanded = isMenuExpanded.value,
                        isPaid = invoiceModel.isPaid(),
                        onItemClick = {
                            onMenuClick.invoke(it)
                            isMenuExpanded.value = false
                        },
                        onDismiss = {
                            isMenuExpanded.value = false
                        }
                    )


                }

            }
            Row {
                Text(
                    text = "${stringResource(id = R.string.from)} ${invoiceModel.businessModel?.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${stringResource(id = R.string.to)} ${invoiceModel.customer?.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

            Text(
                text = "$INR ${invoiceModel.invoiceAmount}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = spacing.small, bottom = spacing.small)
            )
            InvoiceMarker(isPaid = invoiceModel.isPaid)
        }

    }
}
//const val INR = "₹"
const val INR = "$"
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun InvoicePreviewLight() {
    InvoiceTheme() {
        InvoiceCard(InvoiceModel(), {}, {})
    }
}