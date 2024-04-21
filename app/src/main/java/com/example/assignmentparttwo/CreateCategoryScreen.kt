package com.example.assignmentparttwo

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentparttwo.location.LocationUtils
import com.example.assignmentparttwo.location.LocationViewModel
import java.util.Calendar
import java.util.Date

@Composable
fun LoginScreen(context: Context, viewModel: LocationViewModel, categoryScreenViewModel: CategoryScreenViewModel) {



    GradientBox(modifier = Modifier.fillMaxSize()) {


        var isSearchVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "Hello, user", style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Icon(modifier = Modifier.padding(start = 190.dp, top = 10.dp),tint = Color.White,imageVector = Icons.Default.Notifications, contentDescription = null)

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center

            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){


                    //CATEGORY NAME
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = "", onValueChange = {}, placeholder = { Text(text = "Category Name")},
                        leadingIcon = { IconButton(onClick = {Log.i("Icon clicked", "you clicked it ")}){
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                        }
                    )

                    //DATE TEXT FIELD
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = categoryScreenViewModel.state.value.date, onValueChange = {categoryScreenViewModel.updateDate(it)}, placeholder = { Text(text = "Date")},
                        leadingIcon = { IconButton(onClick = {
                            val year: Int
                            val month: Int
                            val day: Int

                            val calendar = Calendar.getInstance()
                            year = calendar.get(Calendar.YEAR)
                            month = calendar.get(Calendar.MONTH)
                            day = calendar.get(Calendar.DAY_OF_MONTH)
                            calendar.time = Date()


                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                    categoryScreenViewModel.updateDate("$dayOfMonth/${month + 1}/$year")
                                }, year, month, day
                            )

                            datePickerDialog.show()
                        }) {
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                        }}
                        )

                    //NUMBER OF ITEMS
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = "", onValueChange = {}, placeholder = { Text(text = "Number Of Items")},
                        leadingIcon = {IconButton(onClick = {Log.i("Icon clicked", "you clicked it ")}){
                            Icon(imageVector = Icons.Default.Add, contentDescription = null);
                        }})

                    //LOCATION
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = categoryScreenViewModel.locationState.value.location, onValueChange = {categoryScreenViewModel.updateCurrentLocation(it)}, placeholder = { Text(text = "Location")},
                        leadingIcon = {IconButton(onClick = {isSearchVisible = true}){
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }})

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            //displaying current location if search icon is clicked.
            if(isSearchVisible){
                MyApp(viewModel, categoryScreenViewModel)
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(isSmallScreenHeight()){
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                }else{
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }
                Text(text = "Create Category", style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                if(isSmallScreenHeight()){
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                }else{
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Create")
                }

            }
        }

    }
}

@Composable
fun MyApp(viewModel: LocationViewModel, categoryScreenViewModel: CategoryScreenViewModel){
    val context = LocalContext.current //getting the current context of the activity that we are on
    val locationUtils = LocationUtils(context) //returns true or false if

    locationDisplay(locationUtils = locationUtils, viewModel ,context = context, categoryScreenViewModel)
}

/*The composable below itself is used to set up the user interface for location display
and permissions display handling. This button is set up to create a user interface for obtaining
and updating the user's location.
* */

@Composable
fun locationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context, //The context facilitates the communication between the app that you are building and the Android operating system.
    categoryScreenViewModel: CategoryScreenViewModel
    ){

    val location = viewModel.location.value

    val address = location?.let{
        locationUtils.reverseGeocodeLocation(location) //gets the actual address of the users location.
    }
    //We will use this request for permission launcher only if the user has not granted permission
    //contract is what we are trying to get.
    //onResult is the result of getting what is in the contract.
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                //I have access to your location

                locationUtils.requestLocationUpdates(viewModel = viewModel)
            }else{
                //Ask for permission
                val rationalRequired  = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, //We want to open this rational in the main activity
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                //giving the user good reasons why we want their permission to access their location
                if(rationalRequired){
                    Toast.makeText(context, "Location Permission is required for this feature to work", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Please enable it in the android settings", Toast.LENGTH_LONG).show()

                }

            }
        })


    Column (modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if(location != null){
            categoryScreenViewModel.updateCurrentLocation("$address")
        }else{
            Toast.makeText(context, "Location is not available", Toast.LENGTH_LONG).show()
        }


        Button(onClick = {
            if(locationUtils.hasLocationPermission(context)){
                //Permission already granted update the location
                locationUtils.requestLocationUpdates(viewModel)

            }else{
                //Request location permission by launching the rational
                requestPermissionLauncher.launch(
                    arrayOf(//Since we are requesting multiple permissions they will be in the form of an array.
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }) {
            Text(text = "Get location")
        }
    }

}

