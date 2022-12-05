package com.example.kingofseas.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.GameManager
import com.example.kingofseas.Model.Player

class GameViewModel : ViewModel() {
    fun test() {
        println(GameManager.players[0].name)
    }

    var counter: MutableLiveData<Int> = MutableLiveData(0)
    val player1 = Player("JB", 0,0,0,true, emptyList())
    val player2 = Player("Marty", 1,2,3,true, emptyList())
    val player3 = Player("Alan", 4,3,2,true, emptyList())
    val player4 = Player("Phillipe", 2,3,3,true, emptyList())
    val players: MutableLiveData<List<Player>> = MutableLiveData(listOf(player1, player2, player3, player4))

    fun incrementCounter() {
        counter.postValue(counter.value!!.inc())
    }

    fun counterString(): String {
        return counter.value?.toString() ?: "0"
    }

    fun changeName(position: Int) {
        var temp = players.value
        temp!![position].name = "test"
        players.postValue(temp!!)
    }
}