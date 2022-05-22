package com.example.mathtraining.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mathtraining.database.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream




class CourseRepository private constructor(context: Context) {

    private lateinit var database: CourseDatabase

    private lateinit var courseDatabase: CourseDao


    fun courseListDatabase(): List<ListCourse>{


        return courseDatabase.getAll()
    }

    val course  = GeneralCourse.course
    val _selectedСourse: MutableLiveData<Course> = MutableLiveData()

    init {
        GeneralCourse.changeCourse(SelectedCourse.ElementaryCourse)
        GlobalScope.launch (Dispatchers.IO) {
            database = Room.databaseBuilder(
                context,
                CourseDatabase::class.java,
                "course_database"
            ).createFromAsset("example3.db").fallbackToDestructiveMigration().build()
            courseDatabase = database.courseCourseDao()
        }

    }


    companion object{
        private var INSTANCE: CourseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CourseRepository(context)


            }
        }

        fun get(): CourseRepository {
            return INSTANCE ?:
            throw IllegalStateException("Repository must be initialized")
        }
    }
}