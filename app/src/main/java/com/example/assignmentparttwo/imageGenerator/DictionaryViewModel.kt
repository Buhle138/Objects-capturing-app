package com.example.assignmentparttwo.imageGenerator

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentparttwo.CategoryScreenViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

class DictionaryViewModel : ViewModel(){

    val state = mutableStateOf(MyScreenState())
    val listOfItems = mutableStateOf(Items())

    var stateOfItems = mutableStateListOf<Items>()

    var countItems = mutableStateOf(0)

    fun incrementCountItems() {
        countItems.value += 1
    }

    // List holding current items to be added to an object
    private val _items = mutableStateListOf<String>()
    val items: SnapshotStateList<String> get() = _items

    // Method to add a new item to the items list

    fun updateCategoryItBelongTo(categoryItBelongs: String){
        listOfItems.value = listOfItems.value.copy(categoryItBelongs = categoryItBelongs)
    }



    fun increasePageState(pageNumber: Int){

        listOfItems.value = listOfItems.value.copy(pageState = pageNumber)
    }

    fun decreasePageState(pageNumber: Int){
        listOfItems.value = listOfItems.value.copy(pageState = pageNumber)
    }

    fun updateListWithNewItem(categoryItBelongs: String){
        val index = stateOfItems.indexOfFirst { it.categoryItBelongs == categoryItBelongs }
        if (index != -1) {
            val currentItem = stateOfItems[index]
            val updatedListOfItems = currentItem.listOfItems.toMutableList().apply {
                addAll(_items.toList())
            }
            val updatedItem = currentItem.copy(listOfItems = updatedListOfItems)
            stateOfItems[index] = updatedItem
            _items.clear()
        }
    }

    fun addItem(newItem: String) {
        _items.add(newItem)
    }

    // Method to commit items to an ItemObject and clear the items list
    fun commitItems(): Items {
        val newItemObject = Items(listOfItems = _items.toList(), categoryItBelongs = state.value.textState)
        _items.clear()
        return newItemObject
    }


    fun updateText(newText: String){
        state.value = state.value.copy(textState = newText)
    }

    fun updateItemName(newText: String){
        listOfItems.value =  listOfItems.value.copy(nameOfItem = newText)
    }

    //whatever value that was entered is going to be of the new items list.


    fun updateNamesList(newName: String){
        val currentList = listOfItems.value.listOfItems.toMutableList()
        currentList.add(newName)
        listOfItems.value = listOfItems.value.copy(listOfItems = currentList)
    }

    public  fun fetchDefinitions(wordSearch: String){

        viewModelScope.launch {
            try{
                val response = dictionaryResponse.getDefinition(wordSearch)
                response.body()?.first()?.let {
                    state.value = state.value.copy(
                        definition = it.meanings.get(0).definitions.get(0).definition
                    )
                }
            }catch (e: Exception){
                state.value = state.value.copy(
                    error = "Error fetching definitions ${e.message}"
                )
            }
        }
    }

    public fun fetchImage(context: Context){
        viewModelScope.launch {
            try {
                val  imageRespond = imageResponse.searchImage(2,3,"Q8P_E1_LxbbWykHUqps84SdXvWy7cEykVdv-3r0f8_s", state.value.textState)
                state.value = state.value.copy(imageProfile = imageRespond.results.get(0).urls.regular)
                state.value = state.value.copy(secondImage = imageRespond.results.get(1).urls.regular)
                state.value  = state.value.copy(thirdImage = imageRespond.results.get(2).urls.regular)
            }catch (e: Exception){
                Toast.makeText(context, "Please check your internet connection, else the api servers are down!", Toast.LENGTH_LONG).show()
            }
        }
    }



}



data class MyScreenState(
    var textState: String = "",
    var definition: String = "",
    var imageProfile: String = "",
    var secondImage: String = "",
    var thirdImage: String = "",
    var nameOfItem: String = "",
    var namesList: List<String> = mutableListOf(),
    var capturedImageUris: State<List<Uri>> = mutableStateOf<List<Uri>>(emptyList()),
    var imageError: String = "",
    var alternativeImage: String = "",
    val error: String? = null
)
data class Items(
    var pageState: Int = 0,
    var nameOfItem: String = "",
    var listOfItems: List<String> = mutableListOf(),
    var categoryItBelongs: String = ""
)


