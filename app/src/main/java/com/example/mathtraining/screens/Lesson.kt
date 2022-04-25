package com.example.mathtraining.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.viewmodel.StateAnswer
import com.example.mathtraining.viewmodel.TwoBitLessonViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Lesson(
    viewModelTwoBit: TwoBitLessonViewModel = viewModel()
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequest = FocusRequester.Default
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val stateAnswer = viewModelTwoBit.stateAnswer
    val context = LocalContext.current

    val health = viewModelTwoBit.health


    when(stateAnswer.value){
        is StateAnswer.Successfully->{
            Toast.makeText(context, "Красава", Toast.LENGTH_SHORT).show()
        }

        is StateAnswer.Error->{
            Toast.makeText(context, "Неправильно", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {

                InfoForLessons(health.value)

        }
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)) {

            CaseStudy(viewModelTwoBit.firstNum, viewModelTwoBit.secondNum, "plus")
            KeyIMO(
                onShowIME = {


                    focusRequest.requestFocus()
                    keyboard?.show()

                }
            )
            Answer(
                focusRequest,
                focusManager,
                onShowIME={

                    focusRequest.requestFocus()
                    if(it == Inputs.Second){
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                    keyboard?.show()
                },
                onFirst = {
                    viewModelTwoBit.userInputFirst.value = it
                },
                onSecond = {
                    viewModelTwoBit.userInputSecond.value = it
                }
            )

            CheckAnswer(
                onDone={
                    viewModelTwoBit.checkAnswerUser()
                    keyboard?.hide()
                    focusManager.clearFocus()
                }
            )
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
            .padding(horizontal = 10.dp),
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckAnswer(
    onDone: () -> Unit
) {
    Spacer(modifier = Modifier.size(20.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.Black,
        onClick = {
            onDone()
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center){
            Text(text = "DONE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        }
    }
}

enum class Inputs{
    First,
    Second
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Answer(
    focusRequest: FocusRequester,
    focusManager: FocusManager,
    onShowIME: (inputs: Inputs) -> Unit,
    onFirst: (a: String) -> Unit,
    onSecond: (a: String) -> Unit
) {
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
            shape = RoundedCornerShape(30.dp),
            onClick = {
                onShowIME(Inputs.First)
            }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){

                BasicTextField(
                    value = firstNumber.value,
                    onValueChange = {

                        if (firstNumber.value.isEmpty()){
                            firstNumber.value = it
                            onFirst(firstNumber.value)
                        }
                        if (it.isEmpty()){
                            firstNumber.value = ""
                            onFirst("")
                        }

                    },
                    modifier = Modifier.focusRequester(focusRequest),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
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
            onClick = {
                onShowIME(Inputs.Second)
            },
            color = Color.Black,
            shape = RoundedCornerShape(30.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                BasicTextField(
                    value = secondNumber.value,
                    onValueChange = {
                        if (secondNumber.value.isEmpty()){
                            secondNumber.value = it
                            onSecond(secondNumber.value)
                        }
                        if (it.isEmpty()){
                            secondNumber.value = ""
                            onSecond("")
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
                    singleLine = true,

                )
                Text(text = secondNumber.value, color = Color.White ,fontSize =  130.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
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
            .height(80.dp)
            .padding(horizontal = 10.dp),
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
fun InfoForLessons(health: Int) {

    Card(modifier = Modifier.height(100.dp)) {
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {


            Health(health)




            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .padding(start = 20.dp)){
                HowMuch(10, 10)
            }


            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_pause_24),
                    contentDescription = "",
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }

}





@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Health(health: Int) {


    BadgeBox(
        badgeContent = { Text(text = health.toString(),
            fontSize = 14.sp,
            modifier = Modifier
        ) }) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "",
            modifier = Modifier
                .size(80.dp),
            tint = Color.Red
        )
        
    }


}

@Preview
@Composable
fun LessPrev() {
    MaterialTheme() {
        InfoForLessons(10)
    }
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


