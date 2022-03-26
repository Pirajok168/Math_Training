package com.example.mathtraining.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mathtraining.R

sealed class Screens(val route: String, @StringRes val label: Int, @DrawableRes val drawableRes: Int){
    object Lessons: Screens("lessons", R.string.lesson, R.drawable.ic_baseline_calculate_24)
    object Statistic: Screens("statistic", R.string.statistic, R.drawable.ic_baseline_stairs_24)
    object Profile: Screens("profile", R.string.profile, R.drawable.ic_baseline_self_improvement_24)
}