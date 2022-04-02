package com.example.mathtraining.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class AuthViewModel: ViewModel() {

    private val userRepository = UserRepository.get()
    val exists: MutableState<Boolean?> = mutableStateOf(null)

    fun isExists(){
        viewModelScope.launch(Dispatchers.IO) {
            val a =  async { userRepository.isExists() }
            exists.value = a.await()
        }
    }



}