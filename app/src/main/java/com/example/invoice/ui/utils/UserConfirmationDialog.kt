package com.example.invoice.ui.utils

import android.content.res.Configuration
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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


            TextButton(onClick = {
                                    onComplete.invoke(true)
                                 },
                      content = {
                          Text(
                              text = stringResource(id = R.string.ok),
                              color = androidx.compose.material3.MaterialTheme.colorScheme.error
                          )
                      }
            )
        },
        dismissButton = {

                        TextButton(onClick = {
                            onComplete.invoke(false)
                        },
                        content = {
                            Text(text = stringResource(R.string.cancel),
                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                                )
                        }
                            )
        },
        text = {
            Text(text = stringResource(id = R.string.delete_confirmation),style = androidx.compose.material3.MaterialTheme.typography.titleMedium)

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