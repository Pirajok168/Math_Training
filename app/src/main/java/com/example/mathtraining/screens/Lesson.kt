package com.example.mathtraining.screens

import android.graphics.Typeface
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mathtraining.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Lesson() {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequest = FocusRequester.Default
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()



    Scaffold(
        modifier = Modifier,

    ) {
        Column(modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState)) {
            InfoForLessons()
            CaseStudy(38, 21, "plus")
            KeyIMO(
                onShowIME = {


                    focusRequest.requestFocus()
                    keyboard?.show()

                }
            )
            Answer(focusRequest, focusManager)

            CheckAnswer()
        }
    }
}

@Composable
fun CaseStudy(
    firstNumber: Int,
    secondNumber: Int,
    operator: String,
) {
    val iconsOperator = when(operator){
        "plus" -> {
            painterResource(id = R.drawable.ic_baseline_add_24)
        }
        "subtract"->{
            painterResource(id = R.drawable.ic_baseline_remove_24)
        }
        else ->{
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = firstNumber.toString(),
            fontSize = 135.sp,
            fontWeight = FontWeight.ExtraBold
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                painter = iconsOperator!!,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Bottom)
            )

            Text(
                text = secondNumber.toString(),
                fontSize = 135.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
            )
        }
    }
}


@Composable
fun CheckAnswer(

) {
    Spacer(modifier = Modifier.size(20.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.Black
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center){
            Text(text = "DONE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        }
    }
}

@Composable
fun Answer(focusRequest: FocusRequester, focusManager: FocusManager) {
    val firstNumber = remember{
        mutableStateOf("")
    }

    val secondNumber = remember{
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .weight(1f),
            color = Color.Black,
            shape = RoundedCornerShape(30.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){

                BasicTextField(
                    value = firstNumber.value,
                    onValueChange = {
                        if (firstNumber.value.isEmpty()){
                            firstNumber.value = it
                        }

                    },
                    modifier = Modifier.focusRequester(focusRequest),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    singleLine = true
                )
                Text(text = firstNumber.value, color = Color.White, fontSize =  130.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .weight(1f),
            color = Color.Black,
            shape = RoundedCornerShape(30.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                BasicTextField(
                    value = secondNumber.value,
                    onValueChange = {
                        if (secondNumber.value.isEmpty()){
                            secondNumber.value = it
                        }

                    },
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    ),
                    singleLine = true
                )
                Text(text = secondNumber.value, color = Color.White ,fontSize =  130.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun Cells(
    firstNumber: Int,
    secondNumber: Int
) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp), ){
        val height = size.height
        val width = size.width

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 130.sp.toPx()
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = Color.White.hashCode()
            textAlign = android.graphics.Paint.Align.CENTER
        }


        val elemWidth = size.width / 2f

        drawRoundRect(
            color = Color.Black,
            topLeft = Offset(0f, 0f),
            size = Size(elemWidth-10f, height),
            cornerRadius = CornerRadius(40f)
        )

        drawIntoCanvas {
                canvas ->



            canvas.nativeCanvas.drawText(
                firstNumber.toString(),
                (elemWidth-10f) / 2,
                height / 2,
                textPaint,
            )
        }

        drawRoundRect(
            color = Color.Black,
            topLeft = Offset(elemWidth, 0f),
            size = Size(elemWidth, height),
            cornerRadius = CornerRadius(40f)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun KeyIMO(
    onShowIME: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Divider()

            Surface(
                onClick = { onShowIME() },
                modifier = Modifier.size(60.dp),
                color = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_keyboard_24),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                )
            }
        }

    }
}


@Composable
fun InfoForLessons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Health()
        HowMuch(10, 10)

    }
}



@Composable
fun Health() {
    Image(
        painter = painterResource(id = R.drawable.heart),
        contentDescription = "",
        modifier = Modifier
            .size(80.dp)
            .padding(end = 20.dp)
    )
}

@Composable
fun HowMuch(
    passed: Int,
    max: Int,
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(end = 20.dp)
    ){
        val with = size.width
        val height = size.height
        val size = with / max.toFloat()



        var x = 0f
        var y = 0f

        for (i in 0 until passed){

            drawRoundRect(
                color = Color.Black,
                topLeft = Offset(x = x, y = y),
                size = Size(width = size, height = height),
                cornerRadius = CornerRadius(25f)

            )

            x += size + 5
        }
    }
}


@Preview
@Composable
fun LessPrev() {
    MaterialTheme() {
        Lesson()
    }
}