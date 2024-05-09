package com.example.assignmentparttwo.itemsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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


                            IconButton(modifier = Modifier.padding(start = 310.dp),onClick = { scope.launch { drawerState.open() }  }){Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")}

                            IconButton(modifier =  Modifier.padding(start = 260.dp),onClick = {
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .padding(top = 5.dp)
                                        .size(19.dp)
                                        .background(Color(0xFF546A83)),
                                    tint = Color.White,
                                    imageVector = Icons.Default.CameraAlt, contentDescription = null)
                            }
                        }
                    )
                },


                ) {

            }

        }






}