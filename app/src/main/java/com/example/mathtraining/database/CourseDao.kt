package com.example.mathtraining.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface CourseDao{

    @Query("SELECT * FROM course_database")
    fun getAll(): List<ListCourse>
}