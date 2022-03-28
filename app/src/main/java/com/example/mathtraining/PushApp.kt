package com.example.mathtraining

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class PushApp: Application() {

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
    }
}