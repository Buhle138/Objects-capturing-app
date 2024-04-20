package com.example.assignmentparttwo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush


val Blue = Color(0xFF3B5999)
val Tail600 = Color(0xFF59C180)

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){

    Box(
        modifier = modifier.background(brush = Brush.linearGradient(
            listOf(Blue, Tail600)))
    ){
        content()
    }

}