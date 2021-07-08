package com.zd.andrdemo.test

import android.util.Log
import com.zd.andrdemo.TAG
import java.util.*

data class Course(var name: String, var period: Int, var isMust: Boolean = false)

data class Student(
    var name: String,
    var age: Int,
    var isMale: Boolean,
    var courses: List<Course> = listOf()
)


fun testListMethods() {
    val students = listOf(
        Student("taylor", 33, false, listOf(Course("physics", 50), Course("chemistry", 78))),
        Student("milo", 20, false, listOf(Course("computer", 50, true))),
        Student("lili", 40, true, listOf(Course("chemistry", 78), Course("science", 50))),
        Student("meto", 10, false, listOf(Course("mathematics", 48), Course("computer", 50, true)))
    )

    val friends = students
        .flatMap {
            it.courses
        }
        .toSet()
        .filter {
            it.period < 70 && !it.isMust
        }
        .map {
            it.apply {
                name = name.replace(name.first(), name.first().toUpperCase())
            }
        }
        .sortedWith(compareBy({
            it.period
        }, {
            it.name
        }))
    friends.forEach { item ->
        Log.d(TAG, "testListMethods:$item ")
    }
}

fun timeCount(endNum: Int = 60) {
    val mTimer = Timer()
    var time = 0;
    mTimer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            Log.d(TAG, "run: ${++time}")
            if (time % endNum == 0) mTimer.cancel()
        }
    }, 0, 1000)
}