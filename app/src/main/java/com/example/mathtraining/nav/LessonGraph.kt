package com.example.mathtraining.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mathtraining.screens.Lesson

fun NavGraphBuilder.lessonGraph(
    navController: NavController,
){
    navigation(startDestination = Screens.LessonScreen.route, route = LESSON_GRAPH_ROUTE){
        composable(route = Screens.LessonScreen.route){
            Lesson()
        }
    }
}