package com.example.mathtraining.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mathtraining.R

enum class LabelScreens{
    Lessons,
    Statistic,
    Profile,
    Settings,
    MainScreen
}

sealed class Screens(val route: String,  val label: LabelScreens?, @DrawableRes val drawableRes: Int?){
    object Lessons: Screens("lessons",LabelScreens.Lessons, R.drawable.ic_baseline_calculate_24)
    object Statistic: Screens("statistic", LabelScreens.Statistic, R.drawable.ic_baseline_stairs_24)
    object Profile: Screens("profile", LabelScreens.Profile, R.drawable.ic_baseline_self_improvement_24)

    object Settings: Screens("settings", LabelScreens.Settings, R.drawable.ic_baseline_settings_24)
    object MainScreen: Screens("mainScreen", null, null)
    object CreateAccount: Screens("createAccount", null, null)
}