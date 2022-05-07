package com.example.mathtraining.database

import com.example.mathtraining.viewmodel.StateAnswer
import com.google.gson.annotations.SerializedName



data class Course(
    val nameLesson: String,
    val listLessons: List<ListLessons>,
    var passed: Int = 0,
    var complete: Boolean = false
)

data class ListLessons(
    val first: Int,
    val second: Int,
    val operator: String,
){
    val currentAnswer = first + second
}

