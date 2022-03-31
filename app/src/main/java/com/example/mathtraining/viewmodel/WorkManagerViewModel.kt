package com.example.mathtraining.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
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
    val enableNotification = userRepository.isActiveNotification
    val id = userRepository.id

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




    fun replaceWorkManager(){
        workManager.enqueueUniqueWork(
            "sendLog",
            ExistingWorkPolicy.REPLACE,
            uploadWorkRequest as OneTimeWorkRequest
        )
        workManager.getWorkInfosForUniqueWork("sendLog").get()?.forEach {
            Log.e("work", "replace - " + it.state.toString())
        }
    }

    fun isActiveWorkManager(enable: Boolean){

        if (enable){
            workManager?.enqueueUniqueWork(
                "sendLog",
                ExistingWorkPolicy.REPLACE,
                uploadWorkRequest as OneTimeWorkRequest
            )
        }else{
            workManager?.cancelAllWork()
        }

        Log.e("work","$enable -" + workManager?.getWorkInfosForUniqueWork("sendLog")?.get()?.get(0)?.state )
    }

     fun setRunningWorkManager(enable: Boolean) {

        if (enable){
            workManager.enqueueUniqueWork(
                "sendLog",
                    ExistingWorkPolicy.REPLACE,
                uploadWorkRequest as OneTimeWorkRequest
            )
        }
    }

    fun resetWorkManager(){
        workManager.enqueueUniqueWork(
            "sendLog",
            ExistingWorkPolicy.REPLACE,
            uploadWorkRequest as OneTimeWorkRequest
        )
        workManager.getWorkInfosForUniqueWork("sendLog").get()?.forEach {
            Log.e("work", "reset - " + it.state.toString())
        }
    }

    fun updateEnableNotification(enable: Boolean, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateEnableNotification(enable, id)
        }

    }

}