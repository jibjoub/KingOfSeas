package com.example.kingofseas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.GameManager

class GameViewModel : ViewModel() {
    fun test() {
        println(GameManager.players[0].name)
    }
}