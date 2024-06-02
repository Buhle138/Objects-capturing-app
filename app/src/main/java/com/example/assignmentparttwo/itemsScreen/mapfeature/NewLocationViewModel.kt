package com.example.assignmentparttwo.itemsScreen.mapfeature

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

class NewLocationViewModel : ViewModel(){
    //Whenever we change the state at the locationSelectionScreen mutable private state variable below  will be updated.
    private val _location= mutableStateOf<LocationData?>(null)
    val location : State<LocationData?> = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    val address: State<List<GeocodingResult>> = _address

    fun updateLocation(newLocation: LocationData){
        _location.value = newLocation
    }

    //Getting the actual address
    fun fetchAddress(latlng: String){
        try{
            viewModelScope.launch {
                val result = RetrofitClient.create().getAddressFromCoordinates(
                    latlng,
                    "AIzaSyBd1fi2CTBS-CbugE7cAfyr_DUUcBLjPXc"
                )
                _address.value = result.results
            }
        }catch (e: Exception){
            Log.d("res1", "${e.cause} ${e.message}")
        }
    }
}