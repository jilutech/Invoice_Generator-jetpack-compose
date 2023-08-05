package com.example.invoice.ui.home.bussiness

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.invoice.data.home.models.Business
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing
import dagger.Provides

@Composable
fun BusinessCard(business: Business,onClick : () -> Unit){
    
    val spacing = MaterialTheme.spacing

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
            val (refIcon, refName, refAddress, refEmail, refPhone) = createRefs()

            Image(painter = painterResource(id = R.drawable.ic_app_logo),
                  contentDescription = stringResource(id = R.string.empty),
                  modifier = Modifier
                      .size(spacing.large)
                      .constrainAs(refIcon) {
                          start.linkTo(parent.start)
                          top.linkTo(parent.top)
                       }
            )
            
            Text(text = business.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(refName){
                    start.linkTo(refIcon.end,spacing.medium)
                    end.linkTo(parent.end)
                    top.linkTo(refIcon.top)
                    width = Dimension.fillToConstraints
                }
            )
            Text(
                text = business.getCompleteAddress(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.constrainAs(refAddress) {
                    start.linkTo(refName.start)
                    top.linkTo(refName.bottom, spacing.extraSmall)
                    end.linkTo(parent.end, spacing.medium)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun shw(){
    InvoiceTheme() {
        BusinessCard(business = Business(
            "name",
            "aaaa",
            "9999",
            "aaaaa"
        )) {

        }
    }
}