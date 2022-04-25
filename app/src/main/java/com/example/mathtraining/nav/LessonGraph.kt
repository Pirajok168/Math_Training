package com.example.mathtraining.nav

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.mathtraining.screens.Lesson
import com.example.mathtraining.screens.ResultScreen

fun NavGraphBuilder.lessonGraph(
    navController: NavController,
    onContinue: () -> Unit,
    onEnd: () -> Unit,
    onResultScreen: () -> Unit
){
    navigation(startDestination = Screens.LessonScreen.route, route = LESSON_GRAPH_ROUTE){
        composable(route = Screens.LessonScreen.route){
            Lesson(onResultScreen=onResultScreen)
        }

        composable(
            route = "${Screens.ResultScreen.route}?result={result}&isEnd={isEnd}",
            arguments = listOf(
                navArgument("result") { type = NavType.StringType },
                navArgument("isEnd") { type = NavType.BoolType },
            )
        ){
                backStackEntry ->
            ResultScreen(
                result = backStackEntry.arguments?.getString("result")!!,
                isEnd = backStackEntry.arguments?.getBoolean("isEnd")!!,
                onContinue = onContinue,
                onEnd = onEnd
            )
        }
    }
}