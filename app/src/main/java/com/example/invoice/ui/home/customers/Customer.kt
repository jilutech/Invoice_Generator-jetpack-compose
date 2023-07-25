package com.example.invoice.ui.home.customers

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.invoice.R
import com.example.invoice.data.home.repo.models.Customer
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing

@Composable
fun Customer(customer: Customer, onClick : () -> Unit){
    var spacing = MaterialTheme.spacing

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                start = spacing.medium,
                end = spacing.medium,
                top = spacing.medium,
                bottom = spacing.medium
            )
            .clickable { onClick.invoke() }
    ) {

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(spacing.medium)) {

            val (refIcon,refName,refMailId,refPhone,refAddress) = createRefs()


            Image(painter = painterResource(id = R.drawable.ic_app_logo),
                  contentDescription = stringResource( id = R.string.empty),
                  modifier = Modifier
                      .size(spacing.large)
                      .constrainAs(refIcon) {
                          top.linkTo(parent.top)
                          start.linkTo(parent.start)
                      }
                  )
            Text(text = customer.name,
                 style = MaterialTheme.typography.titleLarge,
                 modifier = Modifier
                     .constrainAs(refName){
                         start.linkTo(refIcon.end,spacing.small)
                         top.linkTo(refIcon.top)
                         end.linkTo(parent.end,spacing.medium)
                         width = Dimension.fillToConstraints
                     }
                )

            Text(text = customer.getCompleteAddress(),
                 style = MaterialTheme.typography.bodyMedium,
                 modifier = Modifier
                     .constrainAs(refAddress){
                         top.linkTo(refName.bottom,spacing.small)
                         start.linkTo(refName.start)
                         end.linkTo(parent.end)
                         width = Dimension.fillToConstraints
                     })
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun Preview(){
    InvoiceTheme{
        Customer(
            Customer(
                name ="Jilumon T Jose",
                address = "Thoppil House,Thrickodithanam Chanagancherry",
                phone = "9495576063",
                mailId = "Jilumon@gmail.com")
        ){}
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreView(){
    InvoiceTheme {
        InvoiceTheme{
            Customer(
                Customer(
                    name ="Jilumon T Jose",
                    address = "Thoppil House,Thrickodithanam Chanagancherry",
                    phone = "9495576063",
                    mailId = "Jilumon@gmail.com")
            ){}
        }
    }
}