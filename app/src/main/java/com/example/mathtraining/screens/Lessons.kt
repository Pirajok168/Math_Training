package com.example.mathtraining.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun Lessons(state: ScrollState) {
    SideEffect {
        Log.e("test", "recompose")
    }
    val list = List<Int>(10){
        it + 1
    }
    var i by remember {
        mutableStateOf(0)
    }





    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(state)) {
        Spacer(modifier = Modifier.size(10.dp))
        while (true){
            if (i>=list.size){
                break
            }
            if (i+1>=list.size){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp), horizontalArrangement = Arrangement.Center) {
                    ElemLesson(list[i])
                    i++
                }
                break
            }
            if (i%3 != 0){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    ElemLesson(list[i])
                    i++
                    ElemLesson(list[i])
                    i++
                }
            }else{
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp), horizontalArrangement = Arrangement.Center) {
                    ElemLesson(list[i])
                    i++
                }
            }

        }
        Spacer(modifier = Modifier.size(75.dp))
    }

}

object Gradient{
    val listGradient = mutableListOf(
        Brush.linearGradient(listOf(Color(0xFFF53803),Color(0xFFF5D020))),
        Brush.linearGradient(listOf(Color(0xFFDAD2F3),Color(0xFFFBA8A4))),
        Brush.linearGradient(listOf(Color(0xFFC81F70),Color(0xFFD19592))),
        Brush.linearGradient(listOf(Color(0xFF96E4DF),Color(0xFF4DCCC6))),
        Brush.linearGradient(listOf(Color(0xFF864BA2),Color(0xFFBF3A30))),
        Brush.linearGradient(listOf(Color(0xFF39E5B6),Color(0xFF70B2D9))),
        Brush.linearGradient(listOf(Color(0xFF233329),Color(0xFFABE9CD))),
        Brush.linearGradient(listOf(Color(0xFF14557B),Color(0xFF7FCEC5))),
        Brush.linearGradient(listOf(Color(0xFF3E2F5B),Color(0xFFFFC857))),
        Brush.linearGradient(listOf(Color(0xFFFEB9A3),Color(0xFFFEA684))),
        Brush.linearGradient(listOf(Color(0xFF5899E2),Color(0xFFFFFFFF))),
    )
}

@Composable
fun ElemLesson(i: Int) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(120.dp)
            .background(Gradient.listGradient.random(), CircleShape), contentAlignment = Alignment.Center){
            Text(
                text = i.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 65.sp
            )

        }

        Text(text = "testwww",
            modifier = Modifier
                .requiredWidth(70.dp)
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFF6782B4),
            fontWeight = FontWeight.Bold
        )
    }
    


}

