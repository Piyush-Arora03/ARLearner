package com.example.arlearner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlphabetScreen(navController: NavController) {
    val alphabets= MutableList<String>(26){""}
    for(i in 'A'..'Z'){
        alphabets[i-'A']=i.toString()
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.fillMaxHeight(0.1f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Text("Alphabets", style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.Center))
        }
        FlowRow(verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.
        verticalScroll(rememberScrollState())) {
            alphabets.forEach{
                AlphabetScreenItems(it) {
                    navController.navigate("ARScreen/$it")
                }
            }
        }
    }
}

@Composable
fun AlphabetScreenItems(alphabet:String,onClick:()->Unit){
    val color= remember(alphabet){
        GenerateRandomColor()
    }
    Box(modifier = Modifier
        .size(80.dp)
        .clickable {
            onClick()
    }
        .clip(RoundedCornerShape(20.dp))
        .padding(16.dp)
        .background(color)
    , contentAlignment = Alignment.Center){
        Text(alphabet, fontSize = 24.sp, modifier = Modifier.align(Alignment.Center),
            color = Color.White)
    }
}

fun GenerateRandomColor(): Color{
    val rand= Random(System.currentTimeMillis())
    val red=rand.nextInt(80,180)
    val green=rand.nextInt(180,255)
    val blue=rand.nextInt(180,255)
    val color= Color(red,green,blue)
    return color
}