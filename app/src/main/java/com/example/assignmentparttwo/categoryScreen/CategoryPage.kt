package com.example.assignmentparttwo.categoryScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.assignmentparttwo.CategoryScreenViewModel
import com.example.assignmentparttwo.displayDate
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel

@Composable
fun CategoryPage(myViewModelDictionary: DictionaryViewModel,categoryScreenViewModel: CategoryScreenViewModel, navController: NavHostController, navigateToFirstScreen:() -> Unit){

    GradientBox(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-30).dp)
        ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.35f),

                    ) {

                    Image(
                        modifier = Modifier // Adjust the size of the image as needed
                            .fillMaxSize(),
                        painter = rememberAsyncImagePainter(myViewModelDictionary.state.value.imageProfile),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    IconButton(onClick = {
                        navigateToFirstScreen()
                    }) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp)
                                .background(Color.White),
                            imageVector = Icons.Default.ArrowBack, contentDescription = null)

                    }
                    
                    //Gradient Box
                    ReusableGradient(start = 200f)
                   
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 200.dp, start = 20.dp, end = 20.dp),

                    ){
                        Text("${myViewModelDictionary.state.value.textState} category", style = TextStyle(Color.White, fontSize = 25.sp))

                    }
                }





            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White)
            ) {

                LazyRow(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                ){

                    items(categoryScreenViewModel.listOfObjectsState.value.namesList) { item ->
                        // Your item UI here
                        Button(
                            modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp),
                            onClick = {
                                myViewModelDictionary.fetchDefinitions(item.textState)
                                myViewModelDictionary.updateText(item.textState)
                                myViewModelDictionary.fetchImage()
                            }) {
                            Text(item.textState)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .height(250.dp)
                ) {
                    Image(
                        modifier = Modifier // Adjust the size of the image as needed
                            .height(250.dp)
                            .width(200.dp)
                            .padding(
                                start = 10.dp,
                                end = 20.dp,
                                top = 10.dp,
                            ) // Add padding around the image
                            .clip(RoundedCornerShape(40.dp)) // Apply rounded corners to the image

                        ,
                        painter = rememberAsyncImagePainter(myViewModelDictionary.state.value.secondImage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .height(100.dp)
                        .width(200.dp)
                        .padding(
                            start = 10.dp,
                            end = 20.dp,
                            top = 10.dp,
                        ) // Add padding around the image
                        .clip(RoundedCornerShape(40.dp))){

                        Image(
                            modifier = Modifier.fillMaxSize() // Adjust the size of the image as needed
                              // Apply rounded corners to the image
                            ,
                            painter = rememberAsyncImagePainter(myViewModelDictionary.state.value.thirdImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        ReusableGradient(start = 50f)

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 100.dp, start = 50.dp, end = 20.dp),

                            ){
                           IconButton(onClick = {  navController.navigate("itemsscreen") }) {
                               Icon(
                                   modifier = Modifier
                                       .size(50.dp)
                                       .background(Color.White),
                                   imageVector = Icons.Default.Add, contentDescription = null)
                           }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 200.dp, start = 30.dp, end = 20.dp),

                            ){
                            Text("Add Item", style = TextStyle(Color.White, fontSize = 16.sp))
                        }

                    }
                }

                Text(text = "DETAILS", modifier = Modifier.padding(start = 20.dp,top = 20.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text(myViewModelDictionary.state.value.definition,  modifier = Modifier.padding(horizontal = 20.dp))

                    Button(
                        modifier = Modifier
                            .padding(start = 130.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF18C0C1)),
                        onClick = {  navController.navigate("itemsscreen") }) {
                        Text(text = "Continue")
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Default.ArrowRightAlt, contentDescription = null)
                    }
            }
        }
    }
}

@Composable
fun ReusableGradient(
    start: Float
){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ),
                startY = start
            )
        )
    )
}

