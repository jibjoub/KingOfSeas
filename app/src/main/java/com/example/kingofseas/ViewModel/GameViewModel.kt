package com.example.kingofseas.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.*

class GameViewModel : ViewModel() {
    var counter: MutableLiveData<Int> = MutableLiveData(0)

    var max_number_of_rolls: MutableLiveData<Int> = MutableLiveData(3)
    var remaining_rolls: MutableLiveData<Int> = MutableLiveData(3)


    val player1 = Player("JB", 10,0,0,true, emptyList())
    val player2 = Player("Marty", 10,0,0,false, emptyList())
    val player3 = Player("Alan", 10,0,0,true, emptyList())
    val player4 = Player("Phillipe", 10,0,0,true, emptyList())
    val players: MutableLiveData<List<Player>> = MutableLiveData(listOf(player1, player2, player3, player4))

    var currentPlayerInd = MutableLiveData(0)

    var kingPlayerInd = MutableLiveData(0)

    val dice1 = Dice(DiceFace.FACE_ONE, false)
    val dice2 = Dice(DiceFace.FACE_ONE, false)
    val dice3 = Dice(DiceFace.FACE_TWO, false)
    val dice4 = Dice(DiceFace.FACE_TWO, false)
    val dice5 = Dice(DiceFace.FACE_THREE, false)
    val dice6 = Dice(DiceFace.DAMAGE, false)

    val dices: MutableLiveData<List<Dice>> = MutableLiveData(listOf(dice1, dice2, dice3, dice4, dice5, dice6))

    //Decrement the number of remaining rolls
    fun decrementRolls() {
        remaining_rolls.postValue(remaining_rolls.value!!.dec())
    }

    //Kill or bring back to life a player
    fun changeLiveliness(position: Int) {
        var temp = players.value
        temp!![position].isAlive = !temp!![position].isAlive
        players.postValue(temp!!)
    }

    fun hitPlayers(position: Int, kingPosition: Int, value: Int) {
        var tempPlayer = players.value!!
        if (position == kingPosition) {
            for (i in 0 until tempPlayer.count()) {
                if (tempPlayer[i].name != tempPlayer[position].name)
                    tempPlayer[i].health -= value
                // Kill if needed
                if (tempPlayer[i].health <= 0)
                    tempPlayer[i].isAlive = false
            }
        }
        else
        {
            tempPlayer[kingPosition].health -= value
            // Kill if needed
            if (tempPlayer[kingPosition].health <= 0)
                tempPlayer[kingPosition].isAlive = false
            // TODO Select new king
        }
        players.postValue(tempPlayer)
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

    //Change the selection (if they will be rerolled or not) of the dices[position] dice
    fun changeSelection(position: Int) {
        var temp = dices.value
        temp!![position].isSelected = !temp!![position].isSelected
        dices.postValue(temp!!)
    }

    //Reset dices selction
    fun resetDices() {
        var temp = dices.value
        for (i in 0..(temp!!.size-1)) {
            temp!![i].isSelected = false
        }
        dices.postValue(temp!!)
    }

    //Roll the non selected dices
    fun rollDices(){
        var temp = dices.value
        for (i in 0..(temp!!.size-1)) {
            if (!temp!![i].isSelected) {
                temp!![i].face = DiceFace.values().random()
            }
        }
        dices.postValue(temp!!)
    }

    //Change the currentPlayerInd to the next alive player
    fun nextPlayer(){
        var temp_id = currentPlayerInd.value!!
        temp_id++
        if (temp_id == players.value!!.size)
            temp_id = 0
        while (!players.value!![temp_id].isAlive) {
            if (temp_id == players.value!!.size)
                temp_id = 0
            else
                temp_id += 1
        }
        currentPlayerInd.postValue(temp_id)
        //reset the number of remaining rolls and roll the dices
        resetDices()
        rollDices()
        remaining_rolls.postValue(max_number_of_rolls.value!! - 1)
    }


    //Apply the effects of the final set of dices of a turn on all the players
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
                    // TODO remove modulo ?
                    addToPlayerValue(entry.key, currentPlayerInd.value!!,  diceFaceToInt(entry.key) + entry.value.mod(3))
            }
            else if (entry.key == DiceFace.DAMAGE) {
                hitPlayers(currentPlayerInd.value!!, kingPlayerInd.value!!, entry.value)
            }
            else
                addToPlayerValue(entry.key, currentPlayerInd.value!!, entry.value)
        }

    }


}