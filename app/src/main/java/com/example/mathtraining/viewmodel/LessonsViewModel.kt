package com.example.mathtraining.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mathtraining.repository.CourseRepository

class LessonsViewModel(private val courseRepository: CourseRepository =  CourseRepository.get())
    :ViewModel(){
    val course = courseRepository.course


}