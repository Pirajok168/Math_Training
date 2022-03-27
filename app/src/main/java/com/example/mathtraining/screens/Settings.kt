package com.example.mathtraining.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
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
fun Settings(
    isNightMode: Boolean,
    onChooseLocale: (locale: LocaleApp) -> Unit,
    onChooseNightMode: (isNightMode: Boolean) -> Unit
) {

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

    Surface( modifier = Modifier
        .fillMaxSize(),
        color= MathTheme.colors.backgroundColor[0]
    ) {
        LazyColumn(
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                HeadersSettings(MathTheme.localization.applicationSettings)
            }

            item {
                Notifications(MathTheme.localization.notification, R.drawable.ic_baseline_notifications_24
                    , MathTheme.colors.backgroundColorIconNotifications
                    , MathTheme.colors.tintColorIconNotifications){

                }
            }

            item{
                DarkMode(MathTheme.localization.darkMode, R.drawable.baseline_dark_mode_24
                    , MathTheme.colors.backgroundColorIconDarkMode
                    , MathTheme.colors.tintColorIconDarkMode
                    , isNightMode=isNightMode
                ){
                    onChooseNightMode(it)
                }
            }

            item{
                ChooseLanguage(MathTheme.localization.selectLang, R.drawable.ic_baseline_language_24,image=image
                    , MathTheme.colors.backgroundColorIconChangeLange
                    , MathTheme.colors.tintColorIconChangeLange
                ){
                    onChooseLocale(it)
                }
            }

            item {
                Spacer(modifier = Modifier.size(15.dp))
                HeadersSettings(MathTheme.localization.accountSettings)
            }

            item{
                ChangeCourse(MathTheme.localization.changeCourse, R.drawable.ic_baseline_school_24
                    ,  MathTheme.colors.backgroundColorIconChooseCourse
                    , MathTheme.colors.tintColorIconChooseCourse
                    , idCourse = idCourses
                ){
                    idCourses = it
                }
            }

        }
    }

}


@Composable
fun HeadersSettings(@StringRes label : Int) {
    Text(text = stringResource(id = label)
        , fontSize = 20.sp
        , fontWeight = FontWeight.Bold
        , color = MathTheme.colors.headerColorSetting
    )
    Spacer(modifier = Modifier.size(15.dp))
}