package com.example.mathtraining.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.database.Statistic
import com.example.mathtraining.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AuthViewModel: ViewModel() {

    private val userRepository = UserRepository.get()
    val exists: MutableState<Boolean?> = mutableStateOf(null)
    private val date = Date()
    private val format = SimpleDateFormat("MMM d")
    private val currentDate = SimpleDateFormat("MMM d").format(date)




    private val  _activeUser = userRepository.activeUser
    private var activeUser: ActiveUser? = null
    private var idUser: Int = 0

    init {
        _activeUser.observeForever {
            if (it!=null){
                activeUser = it
                idUser = it.user.id
            }
        }
    }

    fun isExists(){
        viewModelScope.launch(Dispatchers.IO) {
            val a =  async { userRepository.isExists() }
            exists.value = a.await()
        }
    }


    fun validate() = viewModelScope.launch (Dispatchers.IO) {
        val listStatistics = activeUser?.listStatistic?.sortedBy { it.day }
        val lastDate = format.format(listStatistics?.last()?.day)
        if (lastDate != currentDate){
            Log.e("obj", "гей")
            viewModelScope.launch(Dispatchers.IO) {
                userRepository.insertNewValueStatistic(
                    Statistic(Random().nextInt(), idUser, date, 0f)
                )
            }
        }

    }


}