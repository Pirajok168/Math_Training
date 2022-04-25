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
const val AUTH_GRAPH_ROUTE = "auth"
const val ROOT_GRAPH_ROUTE = "root"
const val MAIN_GRAPH_ROUTE = "main"
const val LESSON_GRAPH_ROUTE = "lesson_root"
sealed class Screens(val route: String,  val label: LabelScreens?, @DrawableRes val drawableRes: Int?){
    object Lessons: Screens("lessons",LabelScreens.Lessons, R.drawable.ic_baseline_calculate_24)
    object Statistic: Screens("statistic", LabelScreens.Statistic, R.drawable.ic_baseline_stairs_24)
    object Profile: Screens("profile", LabelScreens.Profile, R.drawable.ic_baseline_self_improvement_24)

    object Settings: Screens("settings", LabelScreens.Settings, R.drawable.ic_baseline_settings_24)
    object MainScreen: Screens("mainScreen", null, null)
    object CreateAccount: Screens("createAccount", null, null)
    object SplashScreen: Screens("splashScreen", null, null)

    object LessonScreen: Screens("lesson_screen", null, null,)
    object ResultScreen: Screens("result_screen", null, null)
}