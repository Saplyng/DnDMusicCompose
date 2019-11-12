package com.example.dndmusiccompose

import androidx.compose.Model
import androidx.compose.frames.ModelList

sealed class Screen {
    object Home: Screen()
    data class Article(val postId: String) : Screen()
    object Interests : Screen()
}

@Model
object AppStatus{
    var currentScreen: Screen = Screen.Home
    val favorites = ModelList<String>()
    val selectedTopics = ModelList<String>()
}

fun navigateTo(destination: Screen){
    AppStatus.currentScreen = destination
}