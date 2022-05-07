package com.example.mathtraining.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mathtraining.ScreenContent
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.screens.CreateAccount
import com.example.mathtraining.screens.Settings
import com.example.mathtraining.viewmodel.ActiveUserViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onNavigation: (screen: Screens, popUpTo: String) -> Unit,
    enableNotification: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit,
    onEnableNotification: (enable: Boolean) -> Unit,
    onLessonScreen: () -> Unit,
    onSettingScreen: () -> Unit,
    onContinue: () -> Unit,
    onEnd: () -> Unit,
    onResultScreen: () -> Unit,
    activeUser: ActiveUserViewModel,
){
    NavHost(navController = navController, startDestination = AUTH_GRAPH_ROUTE, route = ROOT_GRAPH_ROUTE ){
        authGraph(navController = navController, onNavigation)
        lessonGraph(
            navController = navController,
            onContinue = onContinue,
            onEnd = onEnd,
            onResultScreen = onResultScreen
        )

        composable(Screens.CreateAccount.route){
            CreateAccount(onNavigation)
        }

        /*composable(Screens.MainScreen.route){
            ScreenContent(
                onMenuScreen=onSettingScreen,
                onLessonScreen=onLessonScreen
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

        }*/

        mainGraph(
            enableNotification,
            onChooseLocale,
            onChooseNightMode,
            onEnableNotification,
            onSettingScreen,
            onNavigation,
            onLessonScreen,
            activeUser
        )
    }
}