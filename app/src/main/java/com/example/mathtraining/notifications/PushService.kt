package com.example.mathtraining.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService: FirebaseMessagingService() {



    override fun onNewToken(token: String) {
        super.onNewToken(token)


    }



    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("token", "тест - ${message.from}")
    }
}