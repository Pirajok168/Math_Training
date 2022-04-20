package com.example.mathtraining.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class, Statistic::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
}