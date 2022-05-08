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
    object Successful: EventStatistic()
    object Check: EventStatistic()
}

class UserStatisticViewModel: ViewModel() {
    private val userRepository = UserRepository.get()
    private val  _activeUser = userRepository.activeUser
    val eventStatistic: MutableState<EventStatistic> = mutableStateOf(EventStatistic.Check)
    private val activeUser: MutableState<List<Statistic>?> = mutableStateOf(null)
    val listStatistic:  MutableState<List<Statistic>?> = mutableStateOf(null)

    init {
        _activeUser.observeForever {
            activeUser.value = it.listStatistic.sortedBy {
                    stat->
                stat.day
            }.reversed()

            listStatistic.value = it.listStatistic.sortedBy {
                stat->
                    stat.day
            }
        }
    }


    fun changeRange(range: Int){
        eventStatistic.value = EventStatistic.Check
        val list = activeUser.value
        if (range > list?.size!!){
            eventStatistic.value = EventStatistic.Error
        }else{
            eventStatistic.value = EventStatistic.Successful
            listStatistic.value = list.subList(0, range).reversed()
        }

    }

}