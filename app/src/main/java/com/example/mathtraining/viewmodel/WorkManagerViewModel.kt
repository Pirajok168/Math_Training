package com.example.mathtraining.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.mathtraining.database.ActiveUser
import com.example.mathtraining.repository.UserRepository
import com.example.mathtraining.workmanager.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class WorkManagerViewModel  @Inject constructor (
    application: Application,
):  AndroidViewModel(application) {





    private val userRepository = UserRepository.get()

    val user = userRepository.user
    val id = userRepository.id
    val isActiveNotification = userRepository.isActiveNotification

    private val  _activeUser = userRepository.activeUser
    val activeUser
        get() = _activeUser


    private var uploadWorkRequest: WorkRequest =  OneTimeWorkRequestBuilder<UploadWorker>()
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )
        .setInitialDelay(5, TimeUnit.SECONDS)
        .build()

    private var workManager: WorkManager =  WorkManager
        .getInstance(application)

    init {
        isActiveNotification.observeForever {
            if (it !=null){
                setRunningWorkManager(it)
            }
        }
    }







    private fun setRunningWorkManager(enable: Boolean) {

        if (enable){
            workManager.enqueueUniqueWork(
                "sendLog",
                    ExistingWorkPolicy.REPLACE,
                uploadWorkRequest as OneTimeWorkRequest
            )
            Log.e("work", "???????????????? ??????????????????????")
        }else{
            workManager.cancelAllWork()
            Log.e("work", "??????????????????")
        }
    }


    fun updateEnableNotification(enable: Boolean, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateEnableNotification(enable, id)
        }

    }

}