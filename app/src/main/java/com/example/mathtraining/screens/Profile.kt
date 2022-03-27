package com.example.mathtraining.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mathtraining.R
import com.example.mathtraining.itemWorkpiece.CardInfoOrSetting
import com.example.mathtraining.nav.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Profile(navHostController: NavHostController) {
    SideEffect {
        Log.e("test", "profile-recompose")
    }
    Column() {
        Header("6")
        Body(navHostController)

    }
}

@Composable
fun Header(date: String) {
    Row(modifier = Modifier
        .padding(28.dp)
        .height(120.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Surface(
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp),
            color = Color.Transparent,
            border = BorderStroke(4.dp, Brush.linearGradient(listOf(Color(0xFFA7ACD9), Color(0xFF9E8FB2))) )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://www.sunhome.ru/i/wallpapers/73/krasnoe-selo.orig.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .requiredSize(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }



       Surface(
           color = Color.Transparent
       ) {

           Row(modifier = Modifier
               .height(120.dp),

           ) {
               Divider(
                   color = Color.Gray,
                   modifier = Modifier
                       .fillMaxHeight()
                       .width(3.dp)
                       .alpha(0.6f)
               )
               Spacer(modifier = Modifier.size(25.dp))
               Column(
                   modifier = Modifier
                       .height(120.dp),
                   verticalArrangement = Arrangement.Center
               ) {
                   Text(text = stringResource(id = R.string.joined)
                       , color = Color(0xFF745B96)
                       , fontWeight = FontWeight.Bold)

                   Text(buildAnnotatedString {
                       withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                           append(date)
                       }
                       append(" mon ago")
                   })
               }
           }



       }
    }



}


@Composable
fun Body(navHostController: NavHostController) {
    FIO("Данила", "Еремин")
    SettingsAndInfo(navHostController)
}


@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun FIO(_name: String, _surname: String
        , lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var enable by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf(_name)
    }

    var surname by remember {
        mutableStateOf(_surname)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val requester = FocusRequester()
    val scope = rememberCoroutineScope()


    Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 15.dp)) {

        BadgeBox(
            backgroundColor= Color.Transparent,
            badgeContent = {
                IconButton(onClick = {
                    if (enable) {
                        focusManager.clearFocus()
                        enable = false
                        keyboardController?.hide()
                    }else{
                        scope.launch {
                            enable = true
                            delay(1000L)
                            requester.requestFocus()
                            keyboardController?.show()
                        }
                    }


                }) {
                    Icon(imageVector = if (enable) Icons.Default.Done else Icons.Default.Edit
                        , contentDescription = "", tint = if (enable) Color(0xFF745B96) else Color.Black)
                }

            }
        ) {

            BasicTextField(value =name, onValueChange = { name=it},
                textStyle = TextStyle(color = if(enable) Color.Black else Color(0xFF745B96),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold),
                enabled =  enable,
                modifier = Modifier
                    .requiredWidth(250.dp)
                    .focusRequester(requester),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.moveFocus(FocusDirection.Down)
                    })
            )



        }

        BasicTextField(value =surname, onValueChange = { surname=it},
            textStyle = TextStyle(color = if(enable) Color.Black else Color(0xFF9E8FB2),
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium),
            enabled =  enable,
            modifier = Modifier
                .requiredWidth(250.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    enable = false
                })
        )

    }
    DisposableEffect(lifecycleOwner){
        onDispose {
            keyboardController?.hide()
            focusManager.clearFocus()
            enable = false
        }
    }

}

@Composable
fun SettingsAndInfo(navHostController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 15.dp)) {
        CardInfoOrSetting(R.string.setting, R.drawable.ic_baseline_settings_24,  Color(0x6370B2D9), Color(0xFF5899E2)){
            navHostController.navigate(Screens.Settings.route)
        }

        CardInfoOrSetting(label = R.string.learned, icon = R.drawable.ic_baseline_school_24, Color(0x99FEA684), Color(0xFF913613)){

        }
    }
}

