package com.example.mathtraining.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.database.Course
import com.example.mathtraining.viewmodel.LessonsViewModel
import java.util.*

@Composable
fun Lessons(
    state: ScrollState,
    onLessonScreen: () -> Unit,
    lessonsViewModel: LessonsViewModel = viewModel()
) {
    SideEffect {
        Log.e("test", "Lessons-recompose")
    }
    val list by lessonsViewModel.course.observeAsState()

    var i by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.verticalScroll(state)) {
        TableLayout(){
            list?.listCourse?.forEachIndexed {
                index, elem ->
                ElemLesson(i = index, elem =elem, onLessonScreen =onLessonScreen)
            }
        }
        Spacer(modifier = Modifier.size(110.dp))
    }



}


@Composable
fun TableLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ){  measurables, constraints ->

        var maxHeight = 0

        var countRow = 0
        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure each children
            val placeable = measurable.measure(constraints)

            if (countRow == 0) {
                maxHeight += (placeable.height)
                countRow += 1
            }else if (countRow == 1){
                maxHeight += (placeable.height)
                countRow += 1
            }else{
                countRow = 0
            }
            placeable
        }
        countRow = 0


        val height = maxHeight
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        layout(constraints.maxWidth, maxHeight) {

            var yPosition = 0

            val maxWidth = constraints.maxWidth

            placeables.forEachIndexed { index, placeable ->


                var xPos = 0
                if(countRow == 0){
                    countRow += 1
                    xPos = (maxWidth / 2) - (placeable.width / 2)
                }else if (countRow == 1){
                    countRow += 1
                    xPos = 0
                }else{
                    countRow = 0
                    xPos = maxWidth - placeable.width
                }


                placeable.placeRelative(x = xPos, y = yPosition)

                if(countRow == 1 || countRow == 0){
                    yPosition += placeable.height
                }

            }
        }
    }
}


@Preview
@Composable
fun PreviewCustomLayout() {
    val list = List<Int>(10){
        it + 1
    }
    
    MaterialTheme() {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                TableLayout(){
                    list.forEach {
                        //ElemLesson(i = it){}
                    }
                }
            }

        }



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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ElemLesson(
    i: Int,
    elem: Course,
    onLessonScreen: () -> Unit
) {
    Column(modifier = Modifier.width(160.dp).padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(130.dp),
            color = Color.Red,
            shape = CircleShape,
            onClick = { onLessonScreen() }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(
                    text = i.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 65.sp,
                )
            }
        }

        Text(text = elem.nameLesson,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFF6782B4),
            fontWeight = FontWeight.Bold
        )
    }







}

