package com.example.assignmentparttwo.imageGenerator

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class DictionaryViewModel : ViewModel(){

    val state = mutableStateOf(MyScreenState())

    fun updateText(newText: String){
        state.value = state.value.copy(textState = newText)
    }

    fun updateItemName(newText: String){
        state.value = state.value.copy(textState = newText)
    }

    fun updateNamesList(newName: String){
        val currentList = state.value.namesList.toMutableList()
        currentList.add(newName)
        state.value = state.value.copy(namesList = currentList)
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

    public fun fetchImage(){
        viewModelScope.launch {
            try {
                val  imageRespond = imageResponse.searchImage(2,3,"Q8P_E1_LxbbWykHUqps84SdXvWy7cEykVdv-3r0f8_s", state.value.textState)
                state.value = state.value.copy(imageProfile = imageRespond.results.get(0).urls.regular)
                state.value = state.value.copy(secondImage = imageRespond.results.get(1).urls.regular)
                state.value  = state.value.copy(thirdImage = imageRespond.results.get(2).urls.regular)
            }catch (e: Exception){
                state.value = state.value.copy(
                    imageError = "Error fetching definitions ${e.message}"
                )
            }
        }
    }

    private val _capturedImageUris = mutableStateOf<List<Uri>>(emptyList())
    val capturedImageUris: State<List<Uri>> = _capturedImageUris

    fun addCapturedImage(uri: Uri) {
        _capturedImageUris.value += listOf(uri)
        state.value = state.value.copy(capturedImageUris = _capturedImageUris)
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
    var capturedImageUris: MutableState<List<Uri>> = mutableStateOf<List<Uri>>(emptyList()),
    var imageError: String = "",
    var alternativeImage: String = "",
    val error: String? = null
)
data class ItemName(
    var nameOfItem: String = ""
)

data class CategoryObject(
    val firstImage: String = "",
    val secondImage: String = "",
    val thirdImage: String = "",
    val isCurrentScreen: Boolean = false
)

