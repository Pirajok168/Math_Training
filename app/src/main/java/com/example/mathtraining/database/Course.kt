package com.example.mathtraining.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mathtraining.viewmodel.StateAnswer
import com.google.gson.annotations.SerializedName







data class Course(
    val nameLesson: String,
    val listLessons: List<ListLessons>,
    var passed: Int = 0,
    var complete: Boolean = false,
    var currectAnswer: Int = 0,
    var incorrectAnswer: Int = 0,
){
    val isCorrectly: Boolean
        get() {
            return incorrectAnswer == 0
        }
}



data class ElementaryCourseBaseData(
    val nameLesson: String,
    val listLessons: List<ListLessons>,
    var passed: Int = 0,
    var complete: Boolean = false,
    var currectAnswer: Int = 0,
    var incorrectAnswer: Int = 0,
)


@Entity(tableName="course_database")
data class ListCourse(
    @PrimaryKey val id: Int,
    val first: Int,
    val second: Int,
    val operators: String,
)



data class ListLessons(
    val first: Int,
    val second: Int,
    val operator: String,
){
    val currentAnswer = first + second
}

