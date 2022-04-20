package com.example.mathtraining.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.nav.AUTH_GRAPH_ROUTE
import com.example.mathtraining.nav.Screens
import com.example.mathtraining.viewmodel.AuthViewModel

@Composable
fun SplashScreen(
    onNavigation: (screen: Screens, popUpTo: String) -> Unit
) {
    val viewAuthViewModel: AuthViewModel = viewModel()
    val exists = viewAuthViewModel.exists
    when (exists.value) {
        true ->{
            viewAuthViewModel.validate()
            onNavigation(Screens.MainScreen, AUTH_GRAPH_ROUTE)
            Log.e("exists" ,"существует")
        }
        false->{
            onNavigation(Screens.CreateAccount, Screens.SplashScreen.route)
            Log.e("exists" ,"не существует")
        }
    }

    LaunchedEffect(key1 = 0){
        viewAuthViewModel.isExists()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Icon(painter = painterResource(id = R.drawable.ic_baseline_school_24), contentDescription = "")
    }
}