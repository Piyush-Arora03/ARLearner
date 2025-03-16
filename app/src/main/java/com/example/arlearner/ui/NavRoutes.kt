package com.example.arlearner.ui

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object AlphabetScreen

@Serializable
object QuizScreen

@Serializable
data class ArScreen(val model:String)