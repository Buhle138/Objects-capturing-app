package com.example.assignmentparttwo.cameraFeature

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.assignmentparttwo.imageGenerator.Items
import com.example.assignmentparttwo.imageGenerator.MyScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {

    var stateOfImages = mutableStateListOf<CapturedImages>()

    private val _capturedImageUris = mutableStateOf<List<Uri>>(emptyList())
    val capturedImageUris: State<List<Uri>> = _capturedImageUris

    fun addCapturedImage(uri: Uri) {
        _capturedImageUris.value += listOf(uri)
    }
    fun commitImages(categoryItBelongsTo: String): CapturedImages {
        val newImagesObject = CapturedImages(itsCategory = categoryItBelongsTo, capturedImageUris = capturedImageUris.value.toList())
        return newImagesObject
    }

    fun clearCurrentImages(){
        _capturedImageUris.value = emptyList()
        _capturedImageUris.value = _capturedImageUris.value
    }

    val state = mutableStateOf(CapturedImages())

    fun updateCategoryNameOfCapturedUris(newName: String){
        state.value = state.value.copy(itsCategory = newName)
    }


}


data class  CapturedImages(
    var itsCategory: String = "" ,
    var capturedImageUris: List<Uri> = emptyList())