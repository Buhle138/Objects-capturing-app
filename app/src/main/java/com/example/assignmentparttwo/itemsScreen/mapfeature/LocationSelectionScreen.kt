package com.example.assignmentparttwo.itemsScreen.mapfeature

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.assignmentparttwo.cameraFeature.CameraViewModel
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import com.example.assignmentparttwo.itemsScreen.createImageFile
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.io.File
import java.util.Dictionary
import java.util.Objects

@Composable
//when a user clicks on a screen we want the lat and log of that place
//So we add the lat and log to the parameter of this function.
fun LocationSelectionScreen(

    myViewModelDictionary: DictionaryViewModel,
    location: LocationData, //getting the location
    myViewModelCamera: CameraViewModel,
    myViewModelLocationViewModel: NewLocationViewModel,
    onLocationSelected: (LocationData) -> Unit){ //what to do with that location when we have found it

    //user location might change which is why we need to manage their state.
    //this method has the current location of the user because mutables give us the current state
    val userLocation = remember{ mutableStateOf(LatLng(location.latitude, location.longitude)) }

    //variable below we want to know where exactly are we on the google maps selection screen.
    //We want to see where the user is at certain zoom state
    var cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }



    Column {



        Row {
            var newLocation: LocationData
            IconButton(onClick = {
                newLocation = LocationData(userLocation.value.latitude, userLocation.value.longitude)
                onLocationSelected(newLocation)
            }) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.AddLocation, contentDescription = null,
                    tint = Color(0xFFECAD00)
                )
            }

            Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),text = "Address", fontSize = 20.sp)


        }

    }

    //Using  a column because we want to have the google map and underneath we want the button

        GoogleMap(modifier = Modifier
            .padding(top = 50.dp, start = 20.dp, bottom = 100.dp, end = 20.dp)
            .clip(
                RoundedCornerShape(15.dp)
            ),
            cameraPositionState = cameraPositionState,
            //BIG NOTE: ONCE THE USER HAS CLICKED ON THE MAP THE STATE HAS CHANGED ALREADY BEFORE WE COULD EVEN CLICK ON THE GET LOCATION BUTTON.
            onMapClick = {userLocation.value = it} //whatever the user clicked on will be the new user location from the mutable variable above.
        ) {
            //it's the red dot showing the user location
            //Basically the code below is about where should our marker be. In this case it should be wherever the user has decided to point to.
            Marker(state = MarkerState(position = userLocation.value))
        }




}

