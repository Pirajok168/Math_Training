package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.database.User
import com.example.mathtraining.repository.UserRepository
import kotlinx.coroutines.launch

class CreateAccountViewModel: ViewModel() {
    val selectedCountry: MutableState<String> = mutableStateOf("")
    val nameUser: MutableState<String> = mutableStateOf("")
    val surnameUser: MutableState<String> = mutableStateOf("")
    val repositoryUser = UserRepository.get()

    fun createUser(){
        viewModelScope.launch {
            repositoryUser.createUser(
                User(name = nameUser.value, surname = surnameUser.value)
            )
        }
    }
}