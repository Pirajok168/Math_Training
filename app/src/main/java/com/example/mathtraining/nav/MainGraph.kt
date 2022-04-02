package com.example.mathtraining.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mathtraining.ScreenContent
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.screens.CreateAccount
import com.example.mathtraining.screens.Settings
import com.example.mathtraining.screens.SplashScreen

fun NavGraphBuilder.mainGraph(
    isNightMode: Boolean,
    enableNotification: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit,
    onEnableNotification: (enable: Boolean) -> Unit,
    onSettingScreen: () -> Unit,
    onNavigation: (screen: Screens, popUpTo: String) -> Unit
){
    navigation(
        startDestination = Screens.MainScreen.route,
        route = MAIN_GRAPH_ROUTE
    ){
        composable(Screens.CreateAccount.route){
            CreateAccount(onNavigation)
        }

        composable(Screens.MainScreen.route){
            ScreenContent(
                onMenuScreen=onSettingScreen
            )
        }

        composable(Screens.Settings.route){
            Settings(
                isNightMode = isNightMode,
                onChooseLocale = onChooseLocale,
                onChooseNightMode = onChooseNightMode,
                enableNotification = enableNotification,
                onEnableNotification = onEnableNotification
            )

        }
    }

}