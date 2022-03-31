package com.example.mathtraining.database

import android.app.Notification
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_profile")
data class User(
        @PrimaryKey val id: Int = Random().nextInt(),
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "surname") val surname: String,
        @ColumnInfo(name = "isActiveNotification") val isActiveNotification: Boolean = true,
        @ColumnInfo(name = "timeNotification") val timeNotification: String = "",
){

}