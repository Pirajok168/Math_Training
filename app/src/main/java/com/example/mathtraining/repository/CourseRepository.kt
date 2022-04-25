package com.example.mathtraining.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.mathtraining.database.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream


object CourseData{
    val listCourse = listOf<TestCourse>(


    )
}

class CourseRepository private constructor(context: Context) {

    val course  = GeneralCourse.course

    init {
        GeneralCourse.changeCourse(SelectedCourse.ElementaryCourse)
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