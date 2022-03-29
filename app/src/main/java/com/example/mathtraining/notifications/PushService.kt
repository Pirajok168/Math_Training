package com.example.mathtraining.notifications

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.mathtraining.MainActivity
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