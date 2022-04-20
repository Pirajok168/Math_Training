package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*



sealed class Event{
    object Loading: Event()
    object Success: Event()
}

class ActiveUserViewModel: ViewModel() {
    private val userRepository = UserRepository.get()
    private val  _activeUser = userRepository.activeUser
    private var activeUser: ActiveUser? = null
    private var idUser: Int = 0

    val event: MutableState<Event> = mutableStateOf(Event.Loading)

    private val currentDate = Date()

    init {
        _activeUser.observeForever {
            if (it!=null){
                activeUser = it
                idUser = it.user.id
                event.value = Event.Success
            }
        }
    }


     fun validate() {

    }

}