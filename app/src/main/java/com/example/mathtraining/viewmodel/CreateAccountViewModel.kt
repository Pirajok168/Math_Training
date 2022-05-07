package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.database.User
import com.example.mathtraining.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class CreateAccountViewModel: ViewModel() {
    val selectedCountry: MutableState<String> = mutableStateOf("")
    val nameUser: MutableState<String> = mutableStateOf("")
    val surnameUser: MutableState<String> = mutableStateOf("")
    val repositoryUser = UserRepository.get()

    fun createUser(){
        val idUser = java.util.Random().nextInt()
        viewModelScope.launch {

            repositoryUser.testCreate(
                ActiveUser(
                    user = User(name = nameUser.value, surname = surnameUser.value, id = idUser),
                    listStatistic = mutableListOf(Statistic(day = Date(), statTrack = 0f,
                        userOwnerId = idUser, idStatistic = java.util.Random().nextInt()))
                )
            )

            /* repositoryUser.createUser(
                User(name = nameUser.value, surname = surnameUser.value)
            )*/

        }
    }
}