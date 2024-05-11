package com.example.assignmentparttwo.itemsScreen

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    myViewModelDictionary: DictionaryViewModel,
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
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
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
            //scope.launch { drawerState.open()

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

                            IconButton(modifier =  Modifier.padding(start = 280.dp),onClick = {
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .padding(top = 1.dp)
                                        .size(19.dp)
                                        .background(Color(0xFF546A83)),
                                    tint = Color.White,
                                    imageVector = Icons.Default.CameraAlt, contentDescription = null)
                            }

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
                            onClick = {},
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
                            painter = rememberAsyncImagePainter(myViewModelDictionary.state.value.imageProfile),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column {
                        Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp),text = "Burger", fontSize = 20.sp)
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.AddLocation, contentDescription = null,
                            tint = Color(0xFFECAD00)
                        )
                    }
                }
            }




        }


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
                        value = "",
                        onValueChange = {},
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

