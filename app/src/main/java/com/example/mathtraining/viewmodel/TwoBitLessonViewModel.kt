package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathtraining.database.Course
import com.example.mathtraining.database.ListLessons
import com.example.mathtraining.repository.CourseRepository


sealed class StateAnswer{
    data class Error(val result: String): StateAnswer()
    data class Successfully(val result: String): StateAnswer()
    object Check: StateAnswer()
}

class TwoBitLessonViewModel(
    private val courseRepository: CourseRepository =  CourseRepository.get()
): ViewModel() {
    val selectedСourse: MutableLiveData<Course> = courseRepository._selectedСourse

    var countElemForLesson: MutableState<Int> = mutableStateOf(0)
    var passed: MutableState<Int> = mutableStateOf(0)

    val userInputFirst: MutableState<String> = mutableStateOf("")
    val userInputSecond: MutableState<String> = mutableStateOf("")

    var currentAnswer:Int =0




    val elemCourse: MutableState<ListLessons?> = mutableStateOf(null)



    fun fetchData(){
        userInputFirst.value = ""
        userInputSecond.value = ""
        val listLesson = selectedСourse.value?.listLessons!!
        passed.value = selectedСourse.value?.passed!!
        elemCourse.value = listLesson[passed.value]
        currentAnswer =  elemCourse.value?.currentAnswer!!
        countElemForLesson.value = listLesson.size
    }

    val stateAnswer: MutableState<StateAnswer> = mutableStateOf(StateAnswer.Check)

    val health: MutableState<Int> = mutableStateOf(5)

    fun checkAnswerUser(){
        stateAnswer.value = StateAnswer.Check
        try {
            if ("${userInputFirst.value}${userInputSecond.value}".toInt()==currentAnswer){
                stateAnswer.value = StateAnswer.Successfully("Успешно")
                selectedСourse.value?.passed = passed.value + 1
            }else{
                health.value = health.value - 1
                stateAnswer.value = StateAnswer.Error("Ошибка")
            }
        }catch (e: Exception){
            health.value = health.value - 1
            stateAnswer.value = StateAnswer.Error("Ошибка")
        }

    }
}