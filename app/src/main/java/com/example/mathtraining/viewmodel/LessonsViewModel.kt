package com.example.mathtraining.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mathtraining.database.Course
import com.example.mathtraining.repository.CourseRepository

class LessonsViewModel(private val courseRepository: CourseRepository =  CourseRepository.get())
    :ViewModel(){
    val course = courseRepository.course
    val selectedСourse: MutableLiveData<Course> = courseRepository._selectedСourse

    fun chooseСourse(course: Course){
        selectedСourse.value = course
    }

}