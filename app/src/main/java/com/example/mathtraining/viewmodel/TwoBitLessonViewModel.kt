package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


sealed class StateAnswer{
    object Error: StateAnswer()
    object Successfully: StateAnswer()
    object Wait: StateAnswer()
}

class TwoBitLessonViewModel: ViewModel() {
    val firstNum = 38
    val secondNum = 21
    val userInputFirst: MutableState<String> = mutableStateOf("")
    val userInputSecond: MutableState<String> = mutableStateOf("")

    private val currentAnswer = firstNum+secondNum
    val stateAnswer: MutableState<StateAnswer> = mutableStateOf(StateAnswer.Wait)

    fun checkAnswerUser(){
        stateAnswer.value = StateAnswer.Wait

        try {
            if ("${userInputFirst.value}${userInputSecond.value}".toInt()==currentAnswer){
                stateAnswer.value = StateAnswer.Successfully
            }else{
                stateAnswer.value = StateAnswer.Error
            }
        }catch (e: Exception){
            stateAnswer.value = StateAnswer.Error
        }

    }
}