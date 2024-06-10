package com.example.assignmentparttwo.progressPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assignmentparttwo.R
import com.example.assignmentparttwo.imageGenerator.DictionaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, myViewModel: DictionaryViewModel) {





        var presses by remember { mutableIntStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Color(0xFF546A83),
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Progress Page.", color = Color.White)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate("itemsscreen")
                        }) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(Color(0xFF546A83)),
                                tint = Color.White,
                                imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }

                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF546A83),
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                         color = Color.White
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { presses++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {it



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                ProgressBarWithStates(myViewModel.countItems.value)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(bottom = 90.dp)
                        .fillMaxSize()
                        .background(Color.White) // Optional: background color to see the center effect clearly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.progress), // Replace with your actual image resource
                        contentDescription = "Centered Rounded Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(300.dp) // Adjust the size as needed
                            .clip(RoundedCornerShape(16.dp)) // Adjust the corner radius as needed
                    )
                }

            }
    }


}


@Composable
fun ProgressBarWithStates(itemCount: Int) {
    val progress = when {
        itemCount >= UserState.Packrat.threshold -> 1f
        itemCount >= UserState.Collector.threshold -> 0.5f
        itemCount >= UserState.Starter.threshold -> 0.1f
        else -> 0f
    }

    val stateText = when {
        itemCount >= UserState.Packrat.threshold -> "Packrat"
        itemCount >= UserState.Collector.threshold -> "Collector"
        itemCount >= UserState.Starter.threshold -> "Starter"
        else -> "No Items"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {
        Text(
            text = stateText,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}
