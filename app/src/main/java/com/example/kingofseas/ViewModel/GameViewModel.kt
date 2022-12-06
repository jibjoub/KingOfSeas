package com.example.kingofseas.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.Dice
import com.example.kingofseas.Model.DiceFace
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

    val dice1 = Dice(DiceFace.FACE_ONE, false)
    val dice2 = Dice(DiceFace.FACE_ONE, false)
    val dice3 = Dice(DiceFace.FACE_TWO, false)
    val dice4 = Dice(DiceFace.FACE_TWO, false)
    val dice5 = Dice(DiceFace.FACE_THREE, false)
    val dice6 = Dice(DiceFace.FACE_THREE, false)

    val dices: MutableLiveData<List<Dice>> = MutableLiveData(listOf(dice1, dice2, dice3, dice4, dice5, dice6))

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

    fun changeSelection(position: Int) {
        var temp = dices.value
        temp!![position].isSelected = !temp!![position].isSelected
        dices.postValue(temp!!)
    }

    fun rollDices(){
        var temp = dices.value
        for (i in 0..(temp!!.size-1)) {
            if (!temp!![i].isSelected) {
                temp!![i].face = DiceFace.values().random()
            }
        }
        dices.postValue(temp!!)
    }


}