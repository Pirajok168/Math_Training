package com.example.mathtraining.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mathtraining.R
import com.example.mathtraining.itemWorkpiece.CardInfoOrSetting
import com.example.mathtraining.nav.Screens

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
                       , color = Color.Gray
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

@Composable
fun FIO(name: String, surname: String) {

    Column(modifier = Modifier.padding(horizontal = 28.dp, vertical = 15.dp)) {
        Text(text = name
            , fontWeight = FontWeight.ExtraBold
            , fontSize = 50.sp
            , color = Color(0xFF745B96)
        )
        
        Text(text = surname, fontWeight = FontWeight.Medium, color =  Color(0xFF9E8FB2), fontSize = 40.sp)
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

