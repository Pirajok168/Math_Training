package com.example.mathtraining.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [ListCourse::class], version = 1, exportSchema = false)
abstract class CourseDatabase: RoomDatabase() {
    abstract fun courseCourseDao(): CourseDao
}