package com.example.mathtraining.firebase

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushService: FirebaseMessagingService() {



    override fun onNewToken(token: String) {
        super.onNewToken(token)


    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach{
                entry ->
            intent.putExtra(entry.key, entry.value)
        }

        sendBroadcast(intent)
    }



    companion object{
        const val INTENT_FILTER = "PUSH_EVENT"
        const val ACTION_SHOW_MESSAGE = "show_message"
        const val KEY_MESSAGE = "message"
        const val KEY_ACTION = "action"
    }
}