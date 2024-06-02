package com.example.assignmentparttwo

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.assignmentparttwo.cameraFeature.CapturedImages
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import com.example.assignmentparttwo.imageGenerator.MyScreenState

class CategoryScreenViewModel : ViewModel(){

    val state = mutableStateOf(Date())

    val locationState = mutableStateOf(CurrentLocation())

    val listOfObjectsState = mutableStateOf(ListOfObjectsCategories())

    val listOfCaputuredImagesState = mutableStateOf(ListOfCapturedImages())


    public fun updateCurrentLocation(newLocation: String){

        locationState.value = locationState.value.copy(location = newLocation)

    }
    public  fun updateDate(newDate: String){
        state.value = state.value.copy(date = newDate)
    }

    public fun updateNamesItemOfObject(){



    }

    public fun updateList(newObject: MyScreenState){
        val currentList = listOfObjectsState.value.namesList.toMutableList()
        currentList.add(newObject)
        listOfObjectsState.value = listOfObjectsState.value.copy(namesList = currentList)
    }

    public  fun updateListOfImages(newUriObject: CapturedImages){
        val  currentList = listOfCaputuredImagesState.value.listOfUris.toMutableList()
        currentList.add(newUriObject)
        listOfCaputuredImagesState.value = listOfCaputuredImagesState.value.copy(listOfUris = currentList)

    }

    //Having a list of captured images Uri
    data class  ListOfCapturedImages(
        var  listOfUris: List<CapturedImages> = mutableListOf()
    )

    //MyScreenState is found in the DictionaryViewModel.
 data class  ListOfObjectsCategories(
     var  namesList: List<MyScreenState> = mutableListOf()
 )

    data class CurrentLocation(var location: String = "")
    data class Date (var date: String ="")



}