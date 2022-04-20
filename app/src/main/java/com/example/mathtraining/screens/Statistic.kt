package com.example.mathtraining.screens

import android.graphics.Typeface
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.viewmodel.UserStatisticViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.io.path.Path





@Composable
fun Statistic(
    innerPadding: PaddingValues,
    userStatisticViewModel: UserStatisticViewModel = viewModel()
) {
    val activeUser = userStatisticViewModel.activeUser.observeAsState(null)

    Scaffold(
        modifier = Modifier.padding(innerPadding)
    ){
        val list = activeUser.value?.listStatistic
        
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
            ,
        ) {
            ChangeRange(
                onClick = {

                }
            )
            LabelCompliment(Color(0xB2F3F3F3), "хорошо")
            Day()
        }
        
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
            , contentAlignment = Alignment.BottomCenter
        ){
            Graph( list?.sortedBy {
                it.day
            } ?: listOf(), Color.Blue, Color.Black)

            
        }

    }

}

@Composable
fun Day(

) {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column() {
            Text(text = "Хорошие дни", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "120", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)

            
        }

        Column() {
            Text(text = "Плохие дни", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "23", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
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
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        onClick = {
            onClick()
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


        var borderX = 0f
        var borderY = 0f


        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 12.sp.toPx()
        }


        /*drawIntoCanvas {
            it.nativeCanvas.drawText()
        }*/

        data.forEach {
            var endX = (size.height * it.statTrack) / max
            endX = canvasHeight-endX

            drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(10f),
                topLeft = Offset(x, endX),
                size = Size(30f, canvasHeight-endX-40)
            )


            drawIntoCanvas {
                    canvas ->



                canvas.nativeCanvas.drawText(
                     format.format(it.day),
                    x-40,
                    canvasHeight,
                     textPaint
                )
            }


            /* drawLine(
                color = borderColor,
                start = Offset(borderX, borderY),
                end = Offset(canvasWidth, borderY),
                strokeWidth = 5f,
            )

            borderY += padding*/
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