package com.example.mathtraining.repository

import android.content.Context
import androidx.room.Room
import com.example.mathtraining.database.User
import com.example.mathtraining.database.UserDatabase

class UserRepository(context: Context) {
    private val database = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_profile"
    ).build()

    private val userDao = database.userDao()
    val user = userDao.getInfoUser()
    val isActiveNotification = userDao.getEnableNotification()
    val id = userDao.getId()

    suspend fun updateEnableNotification(enable: Boolean, id: Int){
        userDao.updateIsActiveNotification(enable, id)
    }

    suspend fun createUser(user: User){
        userDao.insert(user)
    }

    suspend fun isExists(): Boolean{
        return userDao.isExists()
    }



    companion object{
        private var INSTANCE: UserRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
        }

        fun get(): UserRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}