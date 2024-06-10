package com.example.assignmentparttwo.progressPage

enum class UserState (val threshold: Int) {
    Starter(1),
    Collector(3),
    Packrat(10)
}