package com.example.mathtraining.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CreateAccountViewModel: ViewModel() {
    val selectedCountry: MutableState<String> = mutableStateOf("")
    val nameUser: MutableState<String> = mutableStateOf("")
    val surnameUser: MutableState<String> = mutableStateOf("")
}