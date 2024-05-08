package com.example.assignmentparttwo.itemsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel

@Composable
fun ItemScreen(myViewModelDictionary: DictionaryViewModel, navigateToCategoryScreen: () -> Unit) {

    Row (
        modifier = Modifier
            .background(Color(0xFF546A83))
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ){
        //BACK ICON
        Row {
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


            Text(text = "${myViewModelDictionary.state.value.textState} Items", modifier = Modifier.weight(1f, fill = false).padding(top = 5.dp), style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(8.dp))


        Row(modifier = Modifier
            .weight(1f, fill = false)
            .fillMaxWidth()
            .padding(start = 100.dp)) {
            //CAMERA ICON
            IconButton(modifier =  Modifier.padding(horizontal = 15.dp),onClick = {
            }) {
                Icon(
                    modifier = Modifier
                        .padding(top = 13.dp)
                        .size(50.dp)
                        .background(Color(0xFF546A83)),
                    tint = Color.White,
                    imageVector = Icons.Default.CameraAlt, contentDescription = null)
            }

            //HAMBURGER MENU
            IconButton(modifier =  Modifier.padding(5.dp), onClick = {

            }) {
                Icon(
                    modifier = Modifier
                        .size(25.dp)
                        .background(Color(0xFF546A83)),
                    tint = Color.White,
                    imageVector = Icons.Default.DensityMedium, contentDescription = null)
            }
        }
        //CAMERA ICON


    }



}