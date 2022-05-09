package com.example.mathtraining.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.viewmodel.EventStatistic
import com.example.mathtraining.viewmodel.UserStatisticViewModel
import java.text.SimpleDateFormat


@Composable
fun Statistic(
    innerPadding: PaddingValues,
    userStatisticViewModel: UserStatisticViewModel = viewModel()
) {
    val activeUser = userStatisticViewModel.listStatistic
    val eventStatistic = userStatisticViewModel.eventStatistic
    val correctAnswer = userStatisticViewModel.correctDay
    val inCorrectAnswer = userStatisticViewModel.inCorrectDay


    when(eventStatistic.value){
        is EventStatistic.Error ->{
            Toast.makeText(LocalContext.current, "Вы ешё недоучились", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        modifier = Modifier.padding(innerPadding)
    ){
        val list = activeUser.value
        
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
            ,
        ) {
            ChangeRange(
                onClick = {
                    userStatisticViewModel.changeRange(it)
                }
            )
            LabelCompliment(Color(0xB2F3F3F3), "хорошо")
            Day(correctAnswer.value, inCorrectAnswer.value)
        }
        
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            , contentAlignment = Alignment.BottomCenter
        ){
            Graph( list ?: listOf(), Color.Blue, Color.Black)

            
        }

    }

}

@Composable
fun Day(value: Int, value1: Int) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column() {
            Text(text = "Хорошие дни", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = value.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

            
        }

        Column() {
            Text(text = "Плохие дни", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = value1.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        }

    }

}


@Composable
fun LabelCompliment(
    backGroundColor: Color,
    efficiency: String,
) {
    Surface(
        color = backGroundColor,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text(
            buildAnnotatedString {
                append("Вы ")

                withStyle(style = SpanStyle(color = Color.Green)){
                    append(efficiency)
                }

                append(" занимаетесь")
            },
            modifier = Modifier.padding(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeRange(
    onClick: (range: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(30.dp),
        onClick = {
            expanded = true
        }
    ) {
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_edit_calendar_24)
                , contentDescription = "")

            Text(text = "Последние 7 дней", modifier = Modifier.padding(start = 8.dp))
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            for (i in 1 .. 7){
                DropdownMenuItem(onClick = {
                    onClick(i)
                    expanded = false
                }) {
                    Text(text = "Последние $i дней")
                }
            }



        }
    }
}


@Composable
fun  Graph(
    data: List<Statistic>,
    color: Color,
    borderColor: Color,
) {


    val format = SimpleDateFormat("MMM d")


    val max = data.maxOf {
        it.statTrack
    }



    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)){
        val canvasWidth = size.width
        val canvasHeight = size.height
        val padding = (canvasWidth ) / (data.size + 1)

        var x = padding
        var y = canvasHeight



        drawLine(
            color = Color.Black,
            start = Offset(20f,0f),
            end = Offset(20f, canvasHeight - 30f),
            strokeWidth = 3f
        )
        drawLine(
            color = Color.Black,
            start = Offset(20f,canvasHeight - 30f),
            end = Offset(canvasWidth, canvasHeight - 30f),
            strokeWidth = 3f
        )

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 12.sp.toPx()
        }


        data.forEach {
            var endX = (size.height * it.statTrack) / max
            endX = canvasHeight-endX




            val endY = if (canvasHeight-endX == 0f) canvasHeight-55f else endX

            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(10f),
                topLeft = Offset(x,  endY),
                size = Size(30f, if(canvasHeight-endX == 0f) 13f else canvasHeight-endX - 45f)
            )


            drawLine(
                color = Color.Black,
                start = Offset(30f,endY),
                end = Offset(x, endY),
                strokeWidth = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            drawIntoCanvas {
                    canvas ->
                canvas.nativeCanvas.drawText(
                    it.statTrack.toInt().toString(),
                    30f,
                    endY - 10f,
                    textPaint
                )
            }


            drawIntoCanvas {
                    canvas ->

                canvas.nativeCanvas.drawText(
                     format.format(it.day),
                    x-40,
                    canvasHeight,
                     textPaint
                )
            }


            x += padding
        }



    }
}


@Preview
@Composable
fun prev() {
    MaterialTheme {
        Statistic(PaddingValues(10.dp))
    }
}