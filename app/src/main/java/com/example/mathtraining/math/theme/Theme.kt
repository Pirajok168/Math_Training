package com.example.mathtraining.math.theme


import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf




@Composable
fun MainTheme(
    locale: LocaleApp,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme){
        false -> {
            baseLightPalette
        }
        true -> {
            baseDarkPalette
        }
    }

    val locale = when(locale){
        is LocaleApp.English ->{
            enLocal
        }
        is LocaleApp.Russian ->{
            ruLocal
        }

    }

    val rippleIndication = rememberRipple()

    CompositionLocalProvider(
        LocalMathColors provides colors,
        LocalMathLocalization provides locale,
        LocalIndication provides rippleIndication,
        content = content
    )
}