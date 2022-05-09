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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mathtraining.math.theme.*
import com.example.mathtraining.nav.*
import com.example.mathtraining.screens.*
import com.example.mathtraining.viewmodel.ActiveUserViewModel
import com.example.mathtraining.viewmodel.Event
import com.example.mathtraining.viewmodel.WorkManagerViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ENABLE_NOTIFICATION = "enable_notification"
class MainActivity : ComponentActivity() {







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            WindowCompat.setDecorFitsSystemWindows(window, false)




            val localeApp: MutableState<LocaleApp> = remember{
                mutableStateOf(LocaleApp.Russian)
            }



            val workManagerViewModel:WorkManagerViewModel = hiltViewModel()
            val activeUserViewModel: ActiveUserViewModel = viewModel()
            val state = activeUserViewModel.event
            when(state.value){
                is Event.Success ->{
                    activeUserViewModel.validate()
                }
            }

            val isNightMode = activeUserViewModel.nightMode

            val enableNotification = workManagerViewModel.isActiveNotification.observeAsState(initial = null)

            val id = workManagerViewModel.id.observeAsState()
            Log.e("user", "enableNotification = " + enableNotification.value.toString())


            val scope = rememberCoroutineScope()


            MainTheme(
                locale = localeApp.value,
                darkTheme = isNightMode.value
            ) {
                val systemUiController = rememberSystemUiController()

                val obj = workManagerViewModel.activeUser.observeAsState()
                Log.e("obj", obj.toString())



                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (id.value == null) {
                            baseDarkPalette.backgroundCreateAccount
                        }else{
                            if(isNightMode.value) baseDarkPalette.backgroundTobBarColor else testLightPalette.backgroundTobBarColor
                        },
                        darkIcons = true
                    )

                    systemUiController.setNavigationBarColor(
                        color = if (id.value == null) baseLightPalette.backgroundInputSurface else Color.White


                    )

                }


                Surface(
                    color = MathTheme.colors.backgroundColor[0],
                ) {
                    ScreenNavigation(
                        onChooseLocale = {
                            localeApp.value = it
                        },
                        onChooseNightMode = {
                            isNightMode.value = it
                        },
                        activeUser=activeUserViewModel,
                        onEnableNotification={

                            workManagerViewModel.updateEnableNotification(it, id.value!!)

                        },
                        enableNotification=enableNotification.value ?: false
                    )
                }

            }
        }
    }

}





@Composable
fun ScreenNavigation(
    enableNotification: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit,
    onEnableNotification: (enable: Boolean) -> Unit,
    activeUser: ActiveUserViewModel,
) {
    val navHostController = rememberNavController()

    SetupNavGraph(
        navController = navHostController,
        enableNotification = enableNotification,
        onChooseNightMode = onChooseNightMode,
        onEnableNotification = onEnableNotification,
        onChooseLocale = onChooseLocale,
        onSettingScreen = {
            navHostController.navigate(Screens.Settings.route){
                launchSingleTop = true
            }
        },
        onNavigation = {
                screen, popUpTo ->
            navHostController.navigate(screen.route){
                popUpTo(popUpTo){
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onLessonScreen={
            navHostController.navigate(Screens.LessonScreen.route){
                popUpTo(MAIN_GRAPH_ROUTE){
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        onEnd = {

        },
        onContinue = {
            navHostController.navigate(Screens.MainScreen.route){
                popUpTo(LESSON_GRAPH_ROUTE) {
                    inclusive = true
                }
                launchSingleTop = true
            }

        },
        onResultScreen = {
            navHostController.navigate("${Screens.ResultScreen.route}?"){
                launchSingleTop = true
            }
        },
        activeUser=activeUser
    )



}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenContent(
    onMenuScreen: () -> Unit,
    onLessonScreen: () -> Unit
) {
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
            .systemBarsPadding()
            //.background(brush)
                ,
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
        backgroundColor = MathTheme.colors.backgroundColor[0]
    ) {
            innerPadding ->

        NavHost(navController, startDestination = Screens.Lessons.route, Modifier.padding(top=innerPadding.calculateTopPadding())) {
            composable(Screens.Statistic.route) {
                Statistic(innerPadding)
            }
            composable(Screens.Lessons.route) {
                Lessons(
                    state,
                    onLessonScreen=onLessonScreen
                )
            }
            composable(Screens.Profile.route) {
                Profile(onMenuScreen)
            }
        }
    }
}

@Composable
fun TopBarLessons() {
    Column {
        TopAppBar(
            title = {
                Text(text = "Course",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MathTheme.colors.accentColor
                )
            },
            backgroundColor= MathTheme.colors.backgroundTobBarColor,
            elevation = 0.dp,
        )
        Divider(
            thickness = 2.dp,
            color = MathTheme.colors.additionalColor,
            modifier = Modifier
                .alpha(0.3f)
        )
    }

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
                    AnimatedVisibility(
                        visible = selected,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
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


