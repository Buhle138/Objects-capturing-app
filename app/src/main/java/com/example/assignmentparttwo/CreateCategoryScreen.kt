package com.example.assignmentparttwo

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Calendar
import java.util.Date

@Composable
fun LoginScreen(context: Context) {



    GradientBox(modifier = Modifier.fillMaxSize()) {

        val date = remember { mutableStateOf("") }

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
                        .background(Color(0xFFEFEEEE)), value = date.value, onValueChange = {it}, placeholder = { Text(text = "Date")},
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
                                    date.value = "$dayOfMonth/${month + 1}/$year"
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
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }})

                    //LOCATION
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField( modifier = Modifier
                        .fillMaxWidth()
                        .width(10.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFEFEEEE)), value = "", onValueChange = {}, placeholder = { Text(text = "Location")},
                        leadingIcon = {IconButton(onClick = {}){
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }})

                    Spacer(modifier = Modifier.height(20.dp))
                }
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
