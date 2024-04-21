package com.example.assignmentparttwo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CategoryScreenViewModel : ViewModel(){

    val state = mutableStateOf(Date())

    val locationState = mutableStateOf(CurrentLocation())


    public fun updateCurrentLocation(newLocation: String){

        locationState.value = locationState.value.copy(location = newLocation)

    }
    public  fun updateDate(newDate: String){

        state.value = state.value.copy(date = newDate)

    }

    data class CurrentLocation(var location: String = "")
    data class Date (var date: String ="")

}