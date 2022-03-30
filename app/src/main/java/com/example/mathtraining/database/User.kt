package com.example.mathtraining.database

import android.app.Notification
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class User(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val name: String,
        val surname: String,
        val isActiveNotification: Notification
)