package com.example.mathtraining.database

import com.google.gson.annotations.SerializedName



data class Course(
    val nameLesson: String,
    val listLessons: List<ListLessons>
)

data class ListLessons(
    val first: String,
    val second: String,
    val operator: String,

)

