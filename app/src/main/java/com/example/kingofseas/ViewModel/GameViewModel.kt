package com.example.kingofseas.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.GameManager
import kotlin.random.Random

class GameViewModel : ViewModel() {
    fun test() {
        println(GameManager.players[0].name)
    }

    fun rollAllDices() {
        GameManager.currentPlayer.dices.forEach({
            it.face = rollDice()
        })
    }

    fun rollDice(): Int {
        return Random.nextInt(1,7)
    }
}