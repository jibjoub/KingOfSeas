package com.example.kingofseas.ViewModel

import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.GameManager

class GameViewModel : ViewModel() {
    fun test() {
        println(GameManager.players)
    }
}