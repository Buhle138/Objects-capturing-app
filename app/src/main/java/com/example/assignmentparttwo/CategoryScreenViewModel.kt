package com.example.assignmentparttwo

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.assignmentparttwo.imageGenerator.MyScreenState

class CategoryScreenViewModel : ViewModel(){

    val state = mutableStateOf(Date())

    val locationState = mutableStateOf(CurrentLocation())

    val listOfObjectsState = mutableStateOf(ListOfObjectsCategories())


    public fun updateCurrentLocation(newLocation: String){

        locationState.value = locationState.value.copy(location = newLocation)

    }
    public  fun updateDate(newDate: String){
        state.value = state.value.copy(date = newDate)
    }



    public fun updateList(newObject: MyScreenState){
        val currentList = listOfObjectsState.value.namesList.toMutableList()
        currentList.add(newObject)
        listOfObjectsState.value = listOfObjectsState.value.copy(namesList = currentList)
    }

    public fun updateUriList(nameOfCurrentState: MyScreenState, uri: Uri){

        for (item in listOfObjectsState.value.namesList){
            if (item.textState == nameOfCurrentState.textState){
                val currentList = mutableStateOf<List<Uri>>(emptyList())
                currentList.value += listOf(uri)
                item.capturedImageUris = currentList

            }
        }

    }

    //MyScreenState is found in the DictionaryViewModel.
 data class  ListOfObjectsCategories(
     var  namesList: List<MyScreenState> = mutableListOf()
 )

    data class CurrentLocation(var location: String = "")
    data class Date (var date: String ="")



}