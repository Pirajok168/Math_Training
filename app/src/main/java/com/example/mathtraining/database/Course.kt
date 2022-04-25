package com.example.mathtraining.database

import com.google.gson.annotations.SerializedName



data class Course(
    @SerializedName("first") val first: String,
    @SerializedName("second") val second: String,
    val operator: String,
    val nameLesson: String

)