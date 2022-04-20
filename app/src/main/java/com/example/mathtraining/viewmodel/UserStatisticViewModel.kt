package com.example.mathtraining.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.repository.UserRepository

class UserStatisticViewModel: ViewModel() {
    private val userRepository = UserRepository.get()
    private val  _activeUser = userRepository.activeUser
    val activeUser
        get() = _activeUser




}