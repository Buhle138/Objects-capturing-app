
package com.example.assignmentparttwo.itemsScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignmentparttwo.CategoryScreenViewModel
import com.example.assignmentparttwo.MainActivity
import com.example.assignmentparttwo.cameraFeature.CameraViewModel
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import com.example.assignmentparttwo.itemsScreen.mapfeature.LocationSelectionScreen
import com.example.assignmentparttwo.itemsScreen.mapfeature.LocationUtils
import com.example.assignmentparttwo.itemsScreen.mapfeature.NewLocationViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

@Composable
fun ItemScreen(
    context: Context,
    myViewModelDictionary: DictionaryViewModel,
    myViewModelCamera: CameraViewModel,
    myViewModelCategory: CategoryScreenViewModel,
    navController: NavHostController,
    locationUtils: LocationUtils,
    myViewModelLocation: NewLocationViewModel,
    navigateToCategoryScreen: () -> Unit
) {


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if(permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                //I have access to your location

                locationUtils.requestLocationUpdates(viewModel = myViewModelLocation)
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

    val items = listOf(
        NavigationItem(
            title = "Home Page",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Category Page",
            selectedIcon = Icons.Filled.DeviceHub,
            unselectedIcon = Icons.Outlined.DeviceHub,
            badgeCount = 45
        ),
        NavigationItem(
            title = "Add New Item",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
        ),
    )

    var showDialog by remember { mutableStateOf(false)}




    //The Camera Icon
    val activity = LocalContext.current as Activity


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }






    //THE CAMERA AND IMAGES

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f
    ) {
        myViewModelCamera.capturedImageUris.value.size
    }



    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->  NavigationDrawerItem(
                    label = { Text(text = item.title) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        val title = item.title

                        if (title == "Category Page"){
                            navigateToCategoryScreen()
                        }else if (title == "Home Page"){
                            navController.navigate("loginscreen")
                        }else if (title == "Add New Item"){
                            showDialog = true
                        }

                        selectedItemIndex = index
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(imageVector = if(index == selectedItemIndex){item.selectedIcon}else{item.unselectedIcon}, contentDescription = item.title)
                    },
                    badge = {
                        item.badgeCount?.let {
                            Text(text = item.badgeCount.toString())
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                }

            }

        },
        drawerState = drawerState
    ) {



        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF546A83),
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary),
                    title = {  },

                    navigationIcon =
                    {
                        IconButton(onClick = {
                            navigateToCategoryScreen()
                        }) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(Color(0xFF546A83)),
                                tint = Color.White,
                                imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }

                        Text(modifier = Modifier.padding(start = 50.dp, top = 11.dp),text = "${myViewModelDictionary.state.value.textState} Items", fontWeight = FontWeight.Bold, fontSize = 20.sp)



                        IconButton(modifier = Modifier.padding(start = 325.dp),onClick = { scope.launch { drawerState.open() }  }){Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")}






                        IconButton(modifier =  Modifier.padding(start = 230.dp),onClick = {
                            showDialog = true
                        }) {
                            Icon(
                                modifier = Modifier
                                    .padding(bottom = 1.dp)
                                    .size(30.dp)
                                    .background(Color(0xFF546A83)),
                                tint = Color.White,
                                imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(RoundedCornerShape(40, 40, 0, 0)),
                    containerColor = Color(0xFF546A83),
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {


                    Button(
                        modifier = Modifier.padding(start = 150.dp),
                        onClick = {
                            val index = myViewModelDictionary.stateOfItems.indexOfFirst { it.categoryItBelongs == myViewModelDictionary.state.value.textState }
                           val newImageObject =  myViewModelCamera.commitImages(myViewModelDictionary.state.value.textState)
                           myViewModelCamera.stateOfImages.add(newImageObject)
                            myViewModelCamera.stateOfImages.forEach{itemObject ->
                                Log.i("UpdatedImages", itemObject.toString())
                            }


                            if (index != -1) {
                                    myViewModelDictionary.updateListWithNewItem(
                                        myViewModelDictionary.state.value.textState
                                    )
                                myViewModelDictionary.stateOfItems.forEach { itemObject ->
                                    Log.i("UpdatedItemInItems", itemObject.toString())
                                }

                            }else if(index == -1){
                                val newItemObject = myViewModelDictionary.commitItems()
                                newItemObject.copy(categoryItBelongs = myViewModelDictionary.state.value.textState)
                                myViewModelDictionary.stateOfItems.add(newItemObject)
                                Log.i("SizeOfSavedItems",  myViewModelDictionary.stateOfItems.size.toString())


                                myViewModelDictionary.stateOfItems.forEach { itemObject ->
                                    Log.i("ItemInItems", itemObject.toString())
                                }
                            }



                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF18C0C1))

                    ) {
                        Text(text = "Save")
                        Icon(imageVector = Icons.Default.ArrowRightAlt, contentDescription = null)
                    }
                }
            },

            ) {


            //The pop up that shows when we click on the add item on the hamburger menu
            if(showDialog){
                //display the alert dialog

                AlertDialog(
                    title = { "Add Category Item"},
                    onDismissRequest = { showDialog=false},
                    confirmButton = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {



                            Button(onClick ={


                                myViewModelDictionary.addItem(myViewModelDictionary.listOfItems.value.nameOfItem)

                                myViewModelDictionary.updateItemName(myViewModelDictionary.listOfItems.value.nameOfItem)
                                //current name of the category
                                myViewModelDictionary.updateCategoryItBelongTo(myViewModelDictionary.state.value.textState)



                            }){
                                Text(text = "Add")
                            }
                            Button(onClick = {
                                showDialog=false
                            }) {
                                Text("Cancel")
                            }

                        }
                    },

                    text = {
                        Column {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                value = myViewModelDictionary.listOfItems.value.nameOfItem,
                                onValueChange = {
                                    myViewModelDictionary.updateItemName(it)
                                },
                                placeholder = { Text(text = "Name Of Item")}

                            )
                        }
                    })
            }

            Column {

                if(myViewModelCamera.capturedImageUris.value.isNotEmpty()){

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.40f)
                            .clip(RoundedCornerShape(0, 0, 15, 16))

                    ) {
                        HorizontalPager(state = pagerState){
                            Image(
                                painter = rememberAsyncImagePainter(myViewModelCamera.capturedImageUris.value[it]),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                        }



                        IconButton(
                            onClick = {
                                Log.i("PageState", myViewModelDictionary.listOfItems.value.pageState.toString())

                                scope.launch {
                                    pagerState.animateScrollToPage(
                                      pagerState.currentPage - 1
                                    )
                                    myViewModelDictionary.decreasePageState(pagerState.currentPage - 1)
                                }


                            },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(  modifier = Modifier
                                .size(30.dp)
                                .background(Color.White),imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Go back")
                        }
                        IconButton(
                            onClick = {
                                Log.i("PageState", myViewModelDictionary.listOfItems.value.pageState.toString())

                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                    myViewModelDictionary.increasePageState(pagerState.currentPage + 1)
                                }

                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(  modifier = Modifier
                                .size(30.dp)
                                .background(Color.White),imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Go Forwared")
                        }


                    }

                }else{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.40f)
                            .clip(RoundedCornerShape(0, 0, 15, 16))

                    ) {

                        Image(
                            modifier = Modifier // Adjust the size of the image as needed
                                .fillMaxSize()
                                .fillMaxWidth()
                                .background(Color.DarkGray)
                            ,
                            painter = rememberAsyncImagePainter(null),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                Row (modifier = Modifier
                    .padding(end = 20.dp, top = 15.dp,)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){

                    myViewModelDictionary.stateOfItems.forEach { itemObject ->
                        val index = myViewModelDictionary.stateOfItems.indexOfFirst { it.categoryItBelongs == myViewModelDictionary.state.value.textState }
                        if (index != -1) {
                            Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),text = itemObject.listOfItems[myViewModelDictionary.listOfItems.value.pageState], fontSize = 20.sp)
                        }

                    }




                    val context = LocalContext.current
                    val file = context.createImageFile()
                    val uri = FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
                        context.packageName + ".provider", file
                    )


                    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {

                        uri?.let {
                            myViewModelCamera.addCapturedImage(uri)
                        }
                    }

                    val permissionLauncher =
                        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
                            if (it) {
                                Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
                                cameraLauncher.launch(uri)
                                val externalCacheFile = File(context.externalCacheDir, file.name)
                                externalCacheFile.delete()
                            } else {
                                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT)
                            }
                        }

                    FloatingActionButton(
                        onClick = {
                            val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

                            if(permissionCheckResult == PackageManager.PERMISSION_GRANTED){
                                cameraLauncher.launch(uri)

                                Log.i("Images", myViewModelCamera.capturedImageUris.value.toString())
                            }else{
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            }


                        }) {
                        Icon(
                            modifier = Modifier
                                .padding(top = 1.dp)
                                .size(25.dp)
                            ,
                            tint = Color.White,
                            imageVector = Icons.Default.CameraAlt, contentDescription = null)
                    }
                }




                if(locationUtils.hasLocationPermission(context)){
                    locationUtils.requestLocationUpdates(myViewModelLocation)
                    myViewModelLocation.location.value?.let { it1->
                        LocationSelectionScreen(myViewModelDictionary,location = it1,myViewModelCamera, myViewModelLocation ,onLocationSelected = {
                            myViewModelLocation.fetchAddress("${it.latitude}, ${it.longitude}")
                            navController.popBackStack()
                        })
                    }

                }else{
                    requestPermissionLauncher.launch(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
                }
            }
        }
    }
    //displaying the Camera Preview.
}



@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}
