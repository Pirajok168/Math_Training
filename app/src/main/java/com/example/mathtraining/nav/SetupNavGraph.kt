package com.example.mathtraining.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mathtraining.ScreenContent
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.screens.CreateAccount
import com.example.mathtraining.screens.Settings

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onNavigation: (screen: Screens, popUpTo: String) -> Unit,
    isNightMode: Boolean,
    enableNotification: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit,
    onEnableNotification: (enable: Boolean) -> Unit,
    onSettingScreen: () -> Unit
){
    NavHost(navController = navController, startDestination = AUTH_GRAPH_ROUTE, route = ROOT_GRAPH_ROUTE ){
        authGraph(navController = navController, onNavigation)

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

        /*mainGraph(
            isNightMode,
            enableNotification,
            onChooseLocale,
            onChooseNightMode,
            onEnableNotification,
            onSettingScreen,
            onNavigation
        )*/
    }
}