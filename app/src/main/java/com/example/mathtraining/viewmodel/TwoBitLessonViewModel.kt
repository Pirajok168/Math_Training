package com.example.mathtraining.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.database.Course
import com.example.mathtraining.database.ListLessons
import com.example.mathtraining.repository.CourseRepository
import com.example.mathtraining.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


sealed class StateAnswer{
    data class Error(val result: String): StateAnswer()
    data class Successfully(val result: String): StateAnswer()
    object Check: StateAnswer()
}

sealed class StateLesson{
    object Loading: StateLesson()
    object Successful: StateLesson()
    data class Error(val message: String): StateLesson()
}


sealed class EventLesson{
    object LoadingLesson: EventLesson()
    object NextLesson: EventLesson()
}

class TwoBitLessonViewModel(
    private val courseRepository: CourseRepository =  CourseRepository.get(),
    private val userRepository: UserRepository = UserRepository.get()
): ViewModel() {
    private val selectedСourse: MutableLiveData<Course> = courseRepository._selectedСourse
    private val activeUser = userRepository.activeUser



    var countElemForLesson: MutableState<Int> = mutableStateOf(0)
    var passed: MutableState<Int> = mutableStateOf(0)

    val userInputFirst: MutableState<String> = mutableStateOf("")
    val userInputSecond: MutableState<String> = mutableStateOf("")

    var currentAnswer: Int =0



    fun event(_eventLesson: EventLesson){
        when(_eventLesson){
            is EventLesson.LoadingLesson->{
                loadingData()
            }
            is EventLesson.NextLesson->{
                fetchLesson()
            }
        }
    }



    val stateAnswer: MutableState<StateAnswer> = mutableStateOf(StateAnswer.Check)
    val stateLesson: MutableState<StateLesson> = mutableStateOf(StateLesson.Loading)
    private val listLessons: MutableState<List<ListLessons>> = mutableStateOf(listOf())
    var health: MutableState<Int> = mutableStateOf(0)
    private fun loadingData(){

        viewModelScope.launch(Dispatchers.IO) {
            health.value = userRepository.getHealth()
            listLessons.value = selectedСourse.value?.listLessons!!
            elemCourse.value = listLessons.value[passed.value]
            currentAnswer = elemCourse.value?.currentAnswer!!
            countElemForLesson.value = listLessons.value.size
            lastElem.value = passed.value + 1  == listLessons.value.size
            health.value = activeUser.value?.user?.health!!
            stateLesson.value = StateLesson.Successful
        }


    }

    private fun fetchLesson(){
        stateLesson.value = StateLesson.Loading
        passed.value += 1
        userInputFirst.value = ""
        userInputSecond.value = ""
        elemCourse.value = listLessons.value[passed.value]
        currentAnswer = elemCourse.value?.currentAnswer!!
        lastElem.value = passed.value + 1  == listLessons.value.size
        stateLesson.value = StateLesson.Successful
    }

    val elemCourse: MutableState<ListLessons?> = mutableStateOf(null)

    val lastElem: MutableState<Boolean> = mutableStateOf(false)





    fun complete(){
        selectedСourse.value?.complete = true
        viewModelScope.launch(Dispatchers.IO) {
            val listStat = activeUser.value?.listStatistic?.sortedBy {
                it.day
            }
            userRepository.addStatTrackStar(listStat?.last()!!,  selectedСourse.value!!, health.value)
        }
    }

    fun checkAnswerUser(){
        stateAnswer.value = StateAnswer.Check
        try {
            if ("${userInputFirst.value}${userInputSecond.value}".toInt()==currentAnswer){
                stateAnswer.value = StateAnswer.Successfully("Успешно")
                selectedСourse.value?.passed = passed.value
            }else{
                health.value = health.value - 1
                selectedСourse.value?.passed = passed.value
                selectedСourse.value?.incorrectAnswer = selectedСourse.value?.incorrectAnswer!! + 1
                stateAnswer.value = StateAnswer.Error("Ошибка")
            }
        }catch (e: Exception){
            health.value = health.value - 1
            selectedСourse.value?.passed = passed.value
            stateAnswer.value = StateAnswer.Error("Ошибка")
            selectedСourse.value?.incorrectAnswer = selectedСourse.value?.incorrectAnswer!! + 1
        }


    }
}