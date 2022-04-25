package com.example.mathtraining.database

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName

data class TestCourse(
    @SerializedName("course") val course: List<Course>,
    val nameCourse: String
)


enum class SelectedCourse{
    ElementaryCourse,
    PreIntermediate
}

interface RoadSoFar{
    fun onDone()
}


object SampleData{
    val listCourse = listOf<Course>(
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "15",
                    second = "14",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),
        Course(
            listLessons = listOf(
                ListLessons(
                    first = "23",
                    second = "43",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "47",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "12",
                    second = "20",
                    operator = "plus",
                ),
                ListLessons(
                    first = "10",
                    second = "23",
                    operator = "plus",
                )

            ),

            nameLesson = "Двоичное вычесление"
        ),

    )
}

object GeneralCourse{
    var course: MutableLiveData<CourseT?> = MutableLiveData(null)

    fun changeCourse(selectedCourse: SelectedCourse){
        this.course.value = when(selectedCourse){
            SelectedCourse.ElementaryCourse->{
                ElementaryCourse(
                    listCourse = SampleData.listCourse,
                    _doneLessons = 0
                )
            }
            else -> {
                ElementaryCourse(
                    listCourse = SampleData.listCourse,
                    _doneLessons = 0
                )
            }
        }
    }
}


open class CourseT(){
    var listCourse: List<Course> = listOf()
    private var completedCourse: Int = 0
    fun endLesson(){
        completedCourse += 1
    }
}


/** Начальный уровень */

data class ElementaryCourse(
    private var _doneLesson: Int,
):RoadSoFar, CourseT(){
    val doneLessons = _doneLesson

    constructor(listCourse: List<Course>, _doneLessons: Int) : this(_doneLessons) {

        this.listCourse = listCourse
    }

    override fun onDone() {
        _doneLesson += 1
    }


}


/** Чуть выше начального уровня */
/*
data class PreIntermediate(
    val listCourse: List<Course>,
    private var _doneLessons: Int
):RoadSoFar{
    val doneLessons = _doneLessons
    override fun onDone() {
        _doneLessons += 1
    }


}*/
