package com.example.assignmentparttwo.cameraFeature

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {

    private val _capturedImageUris = mutableStateOf<List<Uri>>(emptyList())
    val capturedImageUris: State<List<Uri>> = _capturedImageUris

    fun addCapturedImage(uri: Uri) {
        _capturedImageUris.value += listOf(uri)
    }

}