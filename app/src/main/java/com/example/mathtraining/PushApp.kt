package com.example.mathtraining

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import com.example.mathtraining.dto.TestRetrofit
import com.example.mathtraining.notifications.PushService
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.Socket

class PushApp: Application() {

    private lateinit var pushBroadcast: BroadcastReceiver

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
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