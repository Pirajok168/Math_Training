package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathtraining.database.Course
import com.example.mathtraining.repository.CourseRepository


sealed class StateAnswer{
    object Error: StateAnswer()
    object Successfully: StateAnswer()
    object Check: StateAnswer()
}

class TwoBitLessonViewModel(
    private val courseRepository: CourseRepository =  CourseRepository.get()
): ViewModel() {
    val selectedСourse: MutableLiveData<Course> = courseRepository._selectedСourse

    val firstNum = 38
    val secondNum = 21
    val userInputFirst: MutableState<String> = mutableStateOf("")
    val userInputSecond: MutableState<String> = mutableStateOf("")


    private val currentAnswer = firstNum+secondNum
    val stateAnswer: MutableState<StateAnswer> = mutableStateOf(StateAnswer.Check)

    val health: MutableState<Int> = mutableStateOf(5)

    fun checkAnswerUser(){
        stateAnswer.value = StateAnswer.Check
        try {
            if ("${userInputFirst.value}${userInputSecond.value}".toInt()==currentAnswer){
                stateAnswer.value = StateAnswer.Successfully
            }else{
                health.value = health.value - 1
                stateAnswer.value = StateAnswer.Error
            }
        }catch (e: Exception){
            health.value = health.value - 1
            stateAnswer.value = StateAnswer.Error
        }

    }
}