package com.example.mathtraining.database

import android.app.Notification
import androidx.room.*
import com.example.mathtraining.viewmodel.StateAnswer
import java.util.*



@Entity
data class Statistic(
        @PrimaryKey val idStatistic: Int,
        val userOwnerId: Int,
        val day: Date,
        var statTrack: Float,
        var currectAnswer: Int = 0,
        var incorrectAnswer: Int = 0,
)


@Entity(tableName = "user_profile")
data class User(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "surname") val surname: String,
        @ColumnInfo(name = "isActiveNotification") val isActiveNotification: Boolean = true,
        @ColumnInfo(name = "timeNotification") val timeNotification: String = "",
        val health: Int = 5,
)



data class ActiveUser(
        @Embedded val user: User,
        @Relation(
                parentColumn = "id",
                entityColumn = "userOwnerId"
        )
        val listStatistic: MutableList<Statistic>
)