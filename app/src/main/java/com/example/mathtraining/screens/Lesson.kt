package com.example.mathtraining.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathtraining.R
import com.example.mathtraining.viewmodel.EventLesson
import com.example.mathtraining.viewmodel.StateAnswer
import com.example.mathtraining.viewmodel.StateLesson
import com.example.mathtraining.viewmodel.TwoBitLessonViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun Lesson(
    viewModelTwoBit: TwoBitLessonViewModel = viewModel(),
    onResultScreen: () -> Unit
) {
    LaunchedEffect(key1 = 0, block = {
        viewModelTwoBit.event(EventLesson.LoadingLesson)
    })






    val context = LocalContext.current




    val stateLesson = viewModelTwoBit.stateLesson
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val scope = rememberCoroutineScope()


    when(stateLesson.value){
        is StateLesson.Error -> {

        }
        StateLesson.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                , contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        StateLesson.Successful -> {
            MainLessonScreen(viewModelTwoBit,  onResultScreen, drawerState)
            scope.launch {
                drawerState.close()
            }

        }
    }






    
    BackHandler(true) {
        Toast.makeText(context, "Вы уверены, что хотите выйти?", Toast.LENGTH_SHORT).show()
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainLessonScreen(
    viewModelTwoBit: TwoBitLessonViewModel,
    onResultScreen: () -> Unit,
    drawerState: BottomDrawerState
) {
    val course = viewModelTwoBit.elemCourse

    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequest = FocusRequester.Default
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()

    val textResult = remember {
        mutableStateOf(" ")
    }

    val colorResult = remember {
        mutableStateOf(Color.Black)
    }
    val health = viewModelTwoBit.health

    val lastElem = viewModelTwoBit.lastElem
    val stateAnswer = viewModelTwoBit.stateAnswer
    val scope = rememberCoroutineScope()

    when(val state = stateAnswer.value){
        is StateAnswer.Successfully->{
            scope.launch {
                drawerState.open()
            }
            textResult.value = "Правильно"
            colorResult.value = Color.Green
        }

        is StateAnswer.Error->{
            scope.launch {
                drawerState.open()
            }
            textResult.value = "Неправильно"
            colorResult.value = Color.Red
        }
        is StateAnswer.Check->{
            textResult.value = "Проверить"
            colorResult.value = Color.Black
        }
    }

    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            ResultContent(
                textResult.value,
                onNext = {
                    if (lastElem.value){
                        viewModelTwoBit.complete()
                        onResultScreen()
                    }else{
                        viewModelTwoBit.event(EventLesson.NextLesson)
                    }
                }
            )
        },
        drawerBackgroundColor = colorResult.value,
        gesturesEnabled = false,

        ) {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            topBar = {
                InfoForLessons(
                    health.value,
                    viewModelTwoBit.countElemForLesson.value,
                    viewModelTwoBit.passed.value
                )
            }
        ) {
            Column(modifier = Modifier
                .verticalScroll(scrollState)) {

                CaseStudy(
                    course.value?.first ?: 0,
                    course.value?.second ?: 0,
                    course.value?.operator ?: "plus"
                )
                KeyIMO(
                    onShowIME = {


                        focusRequest.requestFocus()
                        keyboard?.show()

                    }
                )
                Answer(
                    viewModelTwoBit.userInputFirst,
                    viewModelTwoBit.userInputSecond,
                    focusRequest,
                    focusManager,
                    onShowIME ={

                        focusRequest.requestFocus()
                        if(it == Inputs.Second){
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                        keyboard?.show()
                    },
                    onFirst = {
                        viewModelTwoBit.userInputFirst.value = it
                    }
                ) {
                    viewModelTwoBit.userInputSecond.value = it
                }

                CheckAnswer(
                    onDone={
                        viewModelTwoBit.checkAnswerUser()
                        keyboard?.hide()
                        focusManager.clearFocus()
                    },
                )
            }
        }
    }
}

@Composable
fun ResultContent(
    text: String,
    onNext: () -> Unit
) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
        ){

            Text(
                text = text,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )


            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = { onNext() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Продолжить")
            }

            Spacer(modifier = Modifier.navigationBarsPadding())

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
    color: Color = Color.Black,
    text: String = "Проверить",
    onDone: () -> Unit
) {
    Spacer(modifier = Modifier.size(20.dp))

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(20.dp),
        color = color,
        onClick = {
            onDone()
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            contentAlignment = Alignment.Center){
            Text(text = text.uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
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
    firstNumber: MutableState<String>,
    secondNumber: MutableState<String>,
    focusRequest: FocusRequester,
    focusManager: FocusManager,
    onShowIME: (inputs: Inputs) -> Unit,
    onFirst: (a: String) -> Unit,
    onSecond: (a: String) -> Unit
) {


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
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){

                BasicTextField(
                    value = firstNumber.value,
                    onValueChange = {

                        if (firstNumber.value.isEmpty()){
                            firstNumber.value = it
                            onFirst(firstNumber.value)
                            focusManager.moveFocus(FocusDirection.Next)
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
                            secondNumber.value = it
                            focusManager.moveFocus(FocusDirection.Left)
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
fun InfoForLessons(health: Int, countElemForLesson: Int, passed: Int) {

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
                HowMuch(passed, countElemForLesson)
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

        for (i in 0 until max){



            val color =if (i in 0..passed){
                Color.Black
            }else{
                Color.LightGray
            }

            drawRoundRect(
                color = color,
                topLeft = Offset(x = x, y = y),
                size = Size(width = size, height = height),
                cornerRadius = CornerRadius(25f)

            )

            x += size + 5
        }
    }
}


