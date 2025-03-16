package com.example.arlearner

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.arlearner.ui.HomeScreen
import com.example.arlearner.ui.QuizScreen
import com.example.arlearner.ui.screens.ARScreen
import com.example.arlearner.ui.screens.AlphabetScreen
import com.example.arlearner.ui.screens.HomeScreen
import com.example.arlearner.ui.theme.ARLearnerTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARLearnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController= rememberNavController()
                    val navHost= NavHost(navController = navController, startDestination = "HomeScreen", modifier = Modifier
                        .padding(innerPadding)){
                        composable("HomeScreen"){
                            HomeScreen(navController)
                        }
                        composable("QuizScreen"){

                        }
                        composable("ARScreen/{alphabet}",
                            arguments = listOf(navArgument("alphabet"){
                                type= NavType.StringType
                            })
                        ){
                            val alphabet=it.arguments!!.getString("alphabet")?:"A"
                                ARScreen(navController,alphabet)
                                Log.d("main",alphabet)
                        }
                        composable("AlphabetScreen"){
                            AlphabetScreen(navController)
                        }
                    }
                }
            }
        }
    }
}