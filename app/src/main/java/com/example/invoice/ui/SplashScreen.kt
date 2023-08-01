package com.example.invoice.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.invoice.LocalNavHost
import com.example.invoice.R
import com.example.invoice.ui.theme.InvoiceTheme
import com.example.invoice.ui.theme.spacing

@Composable
fun SplashScreen() {
    val spacing = androidx.compose.material3.MaterialTheme.spacing
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val controller = LocalNavHost.current

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.app_logo_lottie)
        )
        // to control the animation
        val progress by animateLottieCompositionAsState(
            // pass the composition created above
            composition,
            iterations = 1,
            restartOnPlay = false,
            speed = 0.5f,
        )
        LaunchedEffect(key1 = progress) {
            if (progress == 1f) {
                controller.navigate(AppScreen.Auth.Login.route) {
                    popUpTo(AppScreen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }

        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(spacing.xxxxLarge),
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun pre(){
    InvoiceTheme {
        SplashScreen()
    }
}