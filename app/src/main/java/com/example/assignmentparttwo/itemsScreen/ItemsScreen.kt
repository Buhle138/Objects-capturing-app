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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignmentparttwo.CategoryScreenViewModel
import com.example.assignmentparttwo.cameraFeature.CameraViewModel
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
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
    navigateToCategoryScreen: () -> Unit
) {




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
                                for (item in myViewModelCategory.listOfObjectsState.value.namesList) {
                                    //If the current state variable is equal to the already existing state.
                                    if (myViewModelDictionary.state.value.textState == item.textState) {



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
                                               scope.launch {
                                                   pagerState.animateScrollToPage(
                                                       pagerState.currentPage - 1
                                                   )
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
                                               scope.launch {
                                                   pagerState.animateScrollToPage(
                                                       pagerState.currentPage + 1
                                                   )
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




                    Log.i("Images", myViewModelCamera.capturedImageUris.value.toString())



                    Column {


                        Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),text = myViewModelDictionary.state.value.nameOfItem, fontSize = 20.sp)
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.AddLocation, contentDescription = null,
                            tint = Color(0xFFECAD00)
                        )



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
                                modifier =  Modifier.padding(start = 310.dp, top = 100.dp),
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
                }
            }
        }

    //displaying the Camera Preview.




    //The pop up that shows when we click on the add item on the hamburger menu
    if(showDialog){
        //display the alert dialog

        AlertDialog(
            title = { "Add ${myViewModelDictionary.state.value.textState} Category Item"},
            onDismissRequest = { showDialog=false},
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(onClick ={


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
                        value = myViewModelDictionary.state.value.nameOfItem,
                        onValueChange = {
                                        myViewModelDictionary.updateItemName(it)
                                        myViewModelDictionary.updateNamesList(myViewModelDictionary.state.value.nameOfItem)
                        },
                        placeholder = { Text(text = "Name Of Item")},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            })
    }
}

@Composable
fun ActivityResultComp(){



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

