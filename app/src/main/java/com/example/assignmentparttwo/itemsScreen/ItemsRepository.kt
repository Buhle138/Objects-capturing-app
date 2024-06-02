package com.example.assignmentparttwo.itemsScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.assignmentparttwo.CategoryScreenViewModel
import com.example.assignmentparttwo.cameraFeature.CameraViewModel
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel

class ItemsRepository(val myViewModelCategory: CategoryScreenViewModel, val myViewModelDictionary: DictionaryViewModel,val  myViewModelCamera: CameraViewModel) {
    fun updateItem(myViewModelCategory: CategoryScreenViewModel, myViewModelDictionary: DictionaryViewModel, myViewModelCamera: CameraViewModel){

        //Ensuring that we don't update the current object with empty Uris when we call this updateItem.
        if(myViewModelCamera.capturedImageUris.value.isNotEmpty()){
            var currentItemName = myViewModelDictionary.state.value.textState

            currentItemName.let {
                val currentList = myViewModelCategory.listOfObjectsState.value.namesList.toMutableList()

                val indexOfObject = currentList.indexOfFirst { it.textState == currentItemName }

                if(indexOfObject != -1){
                    val updatedItem = currentList[indexOfObject].copy(capturedImageUris = myViewModelCamera.capturedImageUris)
                    currentList[indexOfObject] = updatedItem

                    myViewModelCategory.listOfObjectsState.value = myViewModelCategory.listOfObjectsState.value.copy(namesList = currentList)
                    myViewModelCamera.clearCurrentImages()
                    Log.i("ListOfObj",  myViewModelCategory.listOfObjectsState.toString())
                }
            }
        }
    }
}