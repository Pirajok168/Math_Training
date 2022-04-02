package com.example.mathtraining.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mathtraining.screens.CreateAccount
import com.example.mathtraining.screens.SplashScreen

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onNavigation: (screen: Screens, popUpTo: String) -> Unit
){
    navigation(
        startDestination = Screens.SplashScreen.route,
        route = AUTH_GRAPH_ROUTE
    ){
        composable(
            route = Screens.SplashScreen.route
        ){
            SplashScreen(onNavigation = onNavigation)
        }
        composable(
            route = Screens.CreateAccount.route
        ){
            CreateAccount(onNavigation)
        }
    }

}