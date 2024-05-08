package com.example.assignmentparttwo.itemCounter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.assignmentparttwo.imageGenerator.MyScreenState

class CounterViewModel : ViewModel(){

    val state = mutableStateOf(Count())

    public  fun updateCounter(newCount: Int){
        state.value = state.value.copy(count = newCount)
    }
    public  fun decrease(){
        state.value = state.value.copy(state.value.count - 1)
    }
    public  fun increase(){
        state.value = state.value.copy(state.value.count + 1)
    }
    data class Count(var count : Int = 0)
}