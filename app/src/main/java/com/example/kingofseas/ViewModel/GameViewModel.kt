package com.example.kingofseas.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.*
import java.util.*

class GameViewModel : ViewModel() {
    fun test() {
        println(GameManager.players[0].name)
    }

    var counter: MutableLiveData<Int> = MutableLiveData(0)

    var max_number_of_rolls: MutableLiveData<Int> = MutableLiveData(3)
    var number_of_rolls: MutableLiveData<Int> = MutableLiveData(3)


    val player1 = Player("JB", 10,0,0,true, emptyList())
    val player2 = Player("Marty", 10,0,0,true, emptyList())
    val player3 = Player("Alan", 10,0,0,true, emptyList())
    val player4 = Player("Phillipe", 10,0,0,true, emptyList())
    val players: MutableLiveData<List<Player>> = MutableLiveData(listOf(player1, player2, player3, player4))

    var currentPlayerInd = MutableLiveData(0)

    val dice1 = Dice(DiceFace.FACE_ONE, false)
    val dice2 = Dice(DiceFace.FACE_ONE, false)
    val dice3 = Dice(DiceFace.FACE_TWO, false)
    val dice4 = Dice(DiceFace.FACE_TWO, false)
    val dice5 = Dice(DiceFace.FACE_THREE, false)
    val dice6 = Dice(DiceFace.FACE_THREE, false)

    val dices: MutableLiveData<List<Dice>> = MutableLiveData(listOf(dice1, dice2, dice3, dice4, dice5, dice6))

    fun decrementRolls() {
        number_of_rolls.postValue(number_of_rolls.value!!.dec())
    }

    fun incrementCounter() {
        number_of_rolls.postValue(counter.value!!.inc())
    }
    fun counterString(): String {
        return counter.value?.toString() ?: "0"
    }

    fun changeName(position: Int) {
        var temp = players.value
        temp!![position].name = "test"
        players.postValue(temp!!)
    }

    //Add the value to the Health, Energy, Winning Point depending on the face to the player at the position in the list
    fun addToPlayerValue(face: DiceFace, position: Int, value: Int) {
        var temp_players = players.value
        if (face == DiceFace.FACE_ONE || face == DiceFace.FACE_TWO || face == DiceFace.FACE_THREE)
            temp_players!![position].winPoint += value
        if (face == DiceFace.HEALTH)
            temp_players!![position].health += value
        if (face == DiceFace.ENERGY)
            temp_players!![position].energy += value
        players.postValue(temp_players!!)
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

    fun nextPlayer(){
        if (currentPlayerInd.value!! == players.value!!.size - 1)
            currentPlayerInd.postValue(0)
        else
            currentPlayerInd.postValue(currentPlayerInd.value!! + 1)
    }


    fun applyChangeEndOfRolls(){
        val num_map = mutableMapOf(DiceFace.FACE_ONE to 0, DiceFace.FACE_TWO to 0, DiceFace.FACE_THREE to 0, DiceFace.HEALTH to 0, DiceFace.ENERGY to 0, DiceFace.DAMAGE to 0)
        //For now it updates each time a change is made but for now it's enough
        for (dice: Dice in dices.value!!) {
            num_map.put(dice.face, num_map.get(dice.face)!! + 1)
        }
        num_map.forEach { entry ->
            //For the dice faces with numbers
            if (entry.key == DiceFace.FACE_ONE || entry.key == DiceFace.FACE_TWO || entry.key == DiceFace.FACE_THREE) {
                if (entry.value >= 3)
                    addToPlayerValue(entry.key, currentPlayerInd.value!!,  diceFaceToInt(entry.key) + entry.value.mod(3))
            }
            //for the non number faces TODO for the damage
            else
                addToPlayerValue(entry.key, currentPlayerInd.value!!, entry.value)
        }

    }


}