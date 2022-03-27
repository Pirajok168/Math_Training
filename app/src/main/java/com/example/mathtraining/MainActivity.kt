package com.example.mathtraining

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.math.theme.MainTheme
import com.example.mathtraining.math.theme.MathTheme
import com.example.mathtraining.nav.LabelScreens
import com.example.mathtraining.nav.Screens
import com.example.mathtraining.screens.Lessons
import com.example.mathtraining.screens.Profile
import com.example.mathtraining.screens.Settings
import com.example.mathtraining.screens.Statistic
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val localeApp: MutableState<LocaleApp> = remember{
                mutableStateOf(LocaleApp.Russian)
            }

            val isNightMode = remember {
                mutableStateOf(false)
            }
            MainTheme(
                locale = localeApp.value,
                darkTheme = isNightMode.value
            ) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }
                ScreenNavigation(
                    onChooseLocale = {
                        localeApp.value = it
                    },
                    onChooseNightMode = {
                        isNightMode.value = it
                    },
                    isNightMode=isNightMode.value
                )

            }
        }
    }
}


@Composable
fun ScreenNavigation(
    isNightMode: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit
) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Screens.MainScreen.route){
        composable(Screens.MainScreen.route){
            ScreenContent(
                onMenuScreen={
                    navHostController.navigate(Screens.Settings.route){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screens.Settings.route){
            Settings(
                isNightMode=isNightMode,
                onChooseLocale = onChooseLocale,
                onChooseNightMode = onChooseNightMode
            )

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenContent(onMenuScreen: () -> Unit) {
    val navController = rememberNavController()
    val listColors = MathTheme.colors.backgroundColor
    val brush = Brush.linearGradient(listColors)
    val state = rememberScrollState()

    SideEffect {
        Log.e("test", "main-recompose")
    }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
        bottomBar = {
            AnimatedVisibility(
                visible = !state.isScrollInProgress,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BottomBar(navController)
            }

        },
        topBar={
            TopBarLessons()

        },
        backgroundColor = Color.Transparent
    ) {
            innerPadding ->

        NavHost(navController, startDestination = Screens.Lessons.route, Modifier.padding(top=innerPadding.calculateTopPadding())) {
            composable(Screens.Statistic.route) {
                Statistic()
            }
            composable(Screens.Lessons.route) {
                Lessons(state)
            }
            composable(Screens.Profile.route) {
                Profile(onMenuScreen)
            }
        }
    }
}

@Composable
fun TopBarLessons() {
    

    
    TopAppBar(
        title = {
            Text(text = "Course", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        },
        backgroundColor= Color.White,
        modifier = Modifier.shadow(100.dp, ambientColor=  Color(
            0xB5D63B0D
        ), spotColor=  Color(0xB5D63B0D))

    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(Screens.Statistic, Screens.Lessons, Screens.Profile)
    val listColors = MathTheme.colors.navBarColor
    val brush = Brush.linearGradient(listColors)


    NavBar(
        Modifier.padding(start = 40.dp, end = 40.dp, bottom = 40.dp),
        shape = RoundedCornerShape(20.dp),
        brush = brush
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach {
            screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = screen.drawableRes!!), contentDescription = null) },
                label = {
                    AnimatedVisibility(visible = selected) {
                        Text(stringResource(id =
                                when(screen.label){
                                    LabelScreens.Lessons -> {
                                        MathTheme.localization.screenLessons
                                    }
                                    LabelScreens.Profile -> {
                                        MathTheme.localization.screenProfile
                                    }
                                    LabelScreens.Statistic -> {
                                        MathTheme.localization.screenStatistic
                                    }
                                    else -> {
                                        MathTheme.localization.screenStatistic
                                    }
                                }

                            )
                        )
                    }

                },
                selected = selected,
                alwaysShowLabel=false,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },
                selectedContentColor = MathTheme.colors.selectedNavItem,
                unselectedContentColor=Color.White,
                modifier=Modifier.scale(if (!selected) 1f else 1.3f)

            )

        }

    }
}

@Composable
fun NavBar(modifier: Modifier = Modifier,
           brush: Brush,
           backgroundColor: Color = MaterialTheme.colors.primarySurface,
           contentColor: Color = contentColorFor(backgroundColor),
           elevation: Dp = BottomNavigationDefaults.Elevation,
           shape: Shape,
           content: @Composable RowScope.() -> Unit
) {
    Surface(
        contentColor = contentColor,
        elevation = elevation,
        modifier = modifier,
        shape = shape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectableGroup()
                .background(brush),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment=Alignment.CenterVertically,
            content = content
        )
    }
}


