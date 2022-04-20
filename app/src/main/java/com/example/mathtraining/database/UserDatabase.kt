package com.example.mathtraining.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [User::class, Statistic::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
}