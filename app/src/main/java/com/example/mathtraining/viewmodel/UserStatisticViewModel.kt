package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.repository.UserRepository

sealed class EventStatistic{
    object Error: EventStatistic()
    data class Successful(val label: String): EventStatistic()
    object Check: EventStatistic()
}

class UserStatisticViewModel: ViewModel() {
    private val userRepository = UserRepository.get()
    private val  _activeUser = userRepository.activeUser
    val eventStatistic: MutableState<EventStatistic> = mutableStateOf(EventStatistic.Check)
    private val activeUser: MutableState<List<Statistic>?> = mutableStateOf(null)
    val listStatistic:  MutableState<List<Statistic>?> = mutableStateOf(null)


    val correctDay: MutableState<Int> =  mutableStateOf(0)
    val inCorrectDay: MutableState<Int> =  mutableStateOf(0)

    init {
        _activeUser.observeForever {
            activeUser.value = it.listStatistic.sortedBy {
                    stat->
                stat.day
            }.reversed()

            correctDay.value = activeUser.value?.sumOf {
                    stat->
                stat.currectAnswer
            }!!

            inCorrectDay.value = activeUser.value?.sumOf {
                    stat->
                stat.incorrectAnswer
            }!!

            listStatistic.value = it.listStatistic.sortedBy {
                stat->
                    stat.day
            }
        }
    }


    fun event(){
        eventStatistic.value = EventStatistic.Check
    }

    fun changeRange(range: Int, label: String){
        val list = activeUser.value
        if (range > list?.size!!){
            eventStatistic.value = EventStatistic.Error
        }else{
            eventStatistic.value = EventStatistic.Successful(label)
            listStatistic.value = list.subList(0, range).reversed()
        }
    }

}