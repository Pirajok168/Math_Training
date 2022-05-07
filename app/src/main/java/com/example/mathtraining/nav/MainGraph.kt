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
import com.example.mathtraining.viewmodel.ActiveUserViewModel

fun NavGraphBuilder.mainGraph(
    enableNotification: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit,
    onEnableNotification: (enable: Boolean) -> Unit,
    onSettingScreen: () -> Unit,
    onNavigation: (screen: Screens, popUpTo: String) -> Unit,
    onLessonScreen: () -> Unit,
    activeUser: ActiveUserViewModel,
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
                onMenuScreen=onSettingScreen,
                onLessonScreen=onLessonScreen
            )
        }

        composable(Screens.Settings.route){
            Settings(
                activeUser = activeUser,
                onChooseLocale = onChooseLocale,
                onChooseNightMode = onChooseNightMode,
                enableNotification = enableNotification,
                onEnableNotification = onEnableNotification
            )

        }

    }

}