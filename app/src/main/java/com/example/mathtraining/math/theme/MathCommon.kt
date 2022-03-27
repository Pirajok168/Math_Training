package com.example.mathtraining.math.theme

import android.media.MediaSession2Service
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class MathColor(
    val selectedNavItem: Color,
    val navBarColor: List<Color>,
    val accentColor: Color,
    val additionalColor: Color,
    val borderProfileColors: List<Color>,
    val backgroundSettingColor: Color,
    val tintSettingsColor: Color,
    val backgroundLearnedColor: Color,
    val tintLearnedColor: Color,
    val backgroundColor: List<Color>,
)


data class Localization(
    val screenProfile: Int,
    val screenStatistic: Int,
    val screenLessons: Int,
    val joined: Int,
    val settings: Int,
    val learned: Int,
    val notification: Int,
    val darkMode: Int,
    val selectLang: Int,
    val changeCourse: Int,
    val applicationSettings: Int,
    val accountSettings: Int,
    val localeApp: LocaleApp
)

object MathTheme{
    val colors: MathColor
        @Composable
        get() = LocalMathColors.current

    val localization: Localization
        @Composable
        get() = LocalMathLocalization.current
}


val LocalMathColors = staticCompositionLocalOf<MathColor> {
    error("No colors provided")
}

val LocalMathLocalization = staticCompositionLocalOf<Localization> {
    error("No local provided")
}