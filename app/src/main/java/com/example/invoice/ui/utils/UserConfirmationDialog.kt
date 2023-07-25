package com.example.invoice.ui.utils

import android.content.res.Configuration
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.invoice.R
import com.example.invoice.ui.theme.InvoiceTheme


@Composable
fun UserConfirmationDialog(onComplete : (shallDeleted : Boolean) -> Unit){


    AlertDialog(
        onDismissRequest = {
                           onComplete.invoke(false)
        },
        confirmButton = {
                    
                        onComplete.invoke(true)
        },
        dismissButton = {
                        onComplete.invoke(false)
        },
        text = {
            stringResource(id = R.string.ok)
        }
        )

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun dial(){
    InvoiceTheme {
        UserConfirmationDialog(::UserConf)
    }
}
fun UserConf(Boboo:Boolean){

}