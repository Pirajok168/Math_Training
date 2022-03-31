package com.example.mathtraining

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.mathtraining.database.User
import com.example.mathtraining.dto.TestRetrofit
import com.example.mathtraining.repository.UserRepository
import com.example.mathtraining.workmanager.CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class PushApp: Application()  {

    private lateinit var pushBroadcast: BroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        UserRepository.initialize(applicationContext)
        createNotificationChannel()
        GlobalScope.launch(Dispatchers.IO) {
            UserRepository.get().createUser(
                User(
                    name = "Данила",
                    surname = "Еремин",
                )
            )
        }
        /*val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()

        WorkManager
            .getInstance(applicationContext)
            .enqueue(uploadWorkRequest)*/





        /*FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
                task ->
            if (!task.isSuccessful){
                return@addOnCompleteListener
            }

            val token = task.result
            Log.e("token", "Token - $token")
        }

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnCompleteListener { task ->
                var msg = "Подписались"
                if (!task.isSuccessful) {
                    msg = "не подписались"
                }
                Log.d("token", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }

        pushBroadcast = object : BroadcastReceiver(){
            override fun onReceive(context:  Context?, intent: Intent?) {
                val extras =intent?.extras
                extras?.keySet()?.firstOrNull {
                    it == PushService.KEY_ACTION
                }?.let {
                        key ->
                    when(extras.getString(key)){
                        PushService.ACTION_SHOW_MESSAGE ->{

                            extras.getString(PushService.KEY_MESSAGE)?.let {
                                Log.e("token", "Сообщение - $it")
                                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                            }
                        }
                        else -> {
                            Toast.makeText(applicationContext, "Ничего нет", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(PushService.INTENT_FILTER)
        registerReceiver(pushBroadcast, intentFilter )

        GlobalScope.launch(Dispatchers.IO) {
            testServer()
        }*/

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun testServer(){
        val test = TestRetrofit.invoke()
        GlobalScope.launch(Dispatchers.IO) {
            val call = test.get()
            Log.e("server", "Ответ - ${call}")
        }

    }



}