package com.example.mathtraining.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathtraining.R
import com.example.mathtraining.itemWorkpiece.ChangeCourse
import com.example.mathtraining.itemWorkpiece.ChooseLanguage
import com.example.mathtraining.itemWorkpiece.DarkMode
import com.example.mathtraining.itemWorkpiece.Notifications
import com.example.mathtraining.math.theme.LocaleApp
import com.example.mathtraining.math.theme.MathTheme

@Composable
fun Settings(onChooseLocale: (locale: LocaleApp) -> Unit ) {

    SideEffect {
        Log.e("test", "Settings-recompose")
    }
    var idCourses by remember {
        mutableStateOf(0)
    }
    val image = when(MathTheme.localization.localeApp){
                is LocaleApp.English ->{
                R.drawable.united_states
            }
                is LocaleApp.Russian ->{
                R.drawable.russia
            }
    }

    LazyColumn(
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
        item {
            HeadersSettings(MathTheme.localization.applicationSettings)
        }

        item {
            Notifications(MathTheme.localization.notification, R.drawable.ic_baseline_notifications_24, Color(0xFF7FCEC5), Color(0xFF14557B)){

            }
        }

        item{
            DarkMode(MathTheme.localization.darkMode, R.drawable.baseline_dark_mode_24, Color(0xFFDEE8EC), Color(0xFF93B0D0)){

            }
        }

        item{
            ChooseLanguage(MathTheme.localization.selectLang, R.drawable.ic_baseline_language_24,image=image, Color(0xFFD6C2BC), Color(0xFFDB1D24)){
                onChooseLocale(it)
            }
        }

        item {
            Spacer(modifier = Modifier.size(15.dp))
            HeadersSettings(MathTheme.localization.accountSettings)
        }

        item{
            ChangeCourse(MathTheme.localization.changeCourse, R.drawable.ic_baseline_school_24,  Color(0x8AE3C09B), Color(0xFF6F6C9E), idCourse = idCourses){
                idCourses = it
            }
        }

    }
}


@Composable
fun HeadersSettings(@StringRes label : Int) {
    Text(text = stringResource(id = label), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF745B96))
    Spacer(modifier = Modifier.size(15.dp))
}