package com.example.mathtraining.database

import android.app.Notification
import androidx.room.*
import java.util.*



@Entity
data class Statistic(
        @PrimaryKey val idStatistic: Int = Random().nextInt(),
        val day: Int,
        val statTrack: Float,
)


@Entity(tableName = "user_profile")
data class User(
        @PrimaryKey val id: Int = Random().nextInt(),
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "surname") val surname: String,
        @ColumnInfo(name = "isActiveNotification") val isActiveNotification: Boolean = true,
        @ColumnInfo(name = "timeNotification") val timeNotification: String = "",
)


data class ActiveUser(
        @Embedded val user: User,
        @Relation(
                parentColumn = "id",
                entityColumn = "idStatistic"
        )
        val listStatistic: List<Statistic>
)