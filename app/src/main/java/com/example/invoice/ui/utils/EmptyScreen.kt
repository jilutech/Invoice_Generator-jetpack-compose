package com.example.invoice.ui.utils

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.invoice.R
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing
 

@Composable
fun EmptyScreen(title: String,onRefresh : () -> Unit) {

    var spacing = androidx.compose.material3.MaterialTheme.spacing
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.medium)
    ) {
        val (refIcon, refTitle, refDesc, refRefresh) = createRefs()
        val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_items))
        val animateProgress by animateLottieCompositionAsState(
            lottieComposition,
            iterations = 1,
            restartOnPlay = false,
            speed = 0.5f
        )
        LottieAnimation(composition = lottieComposition, progress = animateProgress,
                        modifier = Modifier
                            .height(spacing.xxxxLarge)
                            .width(spacing.xxxxLarge)
                            .constrainAs(refIcon) {
                                top.linkTo(parent.top,spacing.xxLarge)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
            contentScale= ContentScale.Fit,
            alignment = Alignment.Center
        )

//        Text(text = title,
//             style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
//             color = MaterialTheme.colors.onSurface,
//             textAlign = TextAlign.Center,
//             modifier = Modifier.constrainAs(refTitle){
//                 start.linkTo(parent.start)
//                 end.linkTo(parent.end)
//                 top.linkTo(refIcon.bottom)
//                 width = Dimension.fillToConstraints
//             }
//            )
        Text(
            text = stringResource(R.string.empty_screen),
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.constrainAs(refDesc){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(refIcon.bottom,spacing.large)
                width = Dimension.fillToConstraints
            }
        )

        Button(onClick = { onRefresh() },
            modifier = Modifier.constrainAs(refRefresh) {
                start.linkTo(parent.start, spacing.xxLarge)
                top.linkTo(refDesc.bottom, spacing.large)
                end.linkTo(parent.end, spacing.xxLarge)
                width = Dimension.fillToConstraints
            }

        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.refresh),
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colors.onError,
            )
        }



    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun prec(){
    InvoiceTheme {
        EmptyScreen(title = "ddddddddd") {
            
        }
    }
}