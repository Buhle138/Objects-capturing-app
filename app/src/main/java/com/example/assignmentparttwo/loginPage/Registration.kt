package com.example.assignmentparttwo.loginPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignmentparttwo.GradientBox

@Composable
fun Registration(navController: NavHostController, myViewModelAuthenticate: AuthenticationViewModel, function: () -> Unit, ) {

    GradientBox(modifier = Modifier.fillMaxSize()) {


        var isSearchVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row {

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
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
                        .background(Color(0xFFEFEEEE)), value = myViewModelAuthenticate.state.value.username, onValueChange = {myViewModelAuthenticate.updateUserName(it)}, placeholder = { Text(modifier = Modifier.padding(start = 83.dp),text = "User Name") },
                        leadingIcon = {
                        }
                    )

                    //DATE TEXT FIELD
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = myViewModelAuthenticate.state.value.email, onValueChange = {myViewModelAuthenticate.updateEmail(it)}, placeholder = { Text(modifier = Modifier.padding(start = 83.dp),text = "Email") },
                        leadingIcon = {
                        }
                    )
                    var isTextFieldFocused by remember { mutableStateOf(false) }

                    //NUMBER OF ITEMS
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .onFocusChanged { isTextFieldFocused = it.isFocused }
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)),
                        value = myViewModelAuthenticate.state.value.password,
                        onValueChange = {
                            myViewModelAuthenticate.updatePassword(it)
                        },
                        placeholder = { Text(modifier = Modifier.padding(start = 83.dp),text = "Password") },
                        leadingIcon = {

                        })




                    //LOCATION
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = myViewModelAuthenticate.state.value.password, onValueChange = {myViewModelAuthenticate.updatePassword(it)}, placeholder = { Text(modifier = Modifier.padding(start = 83.dp),text = "Confirm Password") },
                        leadingIcon = {
                        })

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            //displaying current location if search icon is clicked.



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {






                Row {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF18C0C1)
                        ),
                        onClick = {
                            myViewModelAuthenticate.updateUsersList(myViewModelAuthenticate.state.value)
                            navController.navigate("login")
                        }) {
                        Text(text = "Register")
                    }

                }


                Image(
                    modifier = Modifier
                        .fillMaxWidth() // Adjust the size of the image as needed
                        .height(170.dp)
                        .width(20.dp)
                        .padding(start = 20.dp, end = 20.dp) // Add padding around the image
                        .clip(RoundedCornerShape(40.dp)) // Apply rounded corners to the image
                        .background(Color.DarkGray)

                    ,
                    painter = rememberAsyncImagePainter(null),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )


                Text("",  modifier = Modifier.padding(horizontal = 20.dp))



            }


        }

    }

}