package com.example.kingofseas.ViewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kingofseas.Model.*
import com.example.kingofseas.R

class GameViewModel : ViewModel() {


    var counter: MutableLiveData<Int> = MutableLiveData(0)

    var max_number_of_rolls: MutableLiveData<Int> = MutableLiveData(3)
    var remaining_rolls: MutableLiveData<Int> = MutableLiveData(3)
    var king_being_attacked: MutableLiveData<Boolean> = MutableLiveData(false)
    var is_game_finished: MutableLiveData<Pair<Boolean,String>> = MutableLiveData(Pair(false, ""))


    val player1 = Player("JB", 10,0,0,true, R.drawable.kraken1)
    val player2 = Player("Marty", 10,0,0,true, R.drawable.kraken2)
    val player3 = Player("Alan", 10,0,0,true, R.drawable.kraken3)
    val player4 = Player("Phillipe", 10,0,0,true, R.drawable.sea_monster)
    val players: MutableLiveData<List<Player>> = MutableLiveData(listOf(player1, player2, player3, player4))

    var currentPlayerInd = MutableLiveData(0)

    var kingPlayerInd = MutableLiveData(-1)

    val dice1 = Dice(DiceFace.FACE_ONE, false)
    val dice2 = Dice(DiceFace.FACE_ONE, false)
    val dice3 = Dice(DiceFace.FACE_TWO, false)
    val dice4 = Dice(DiceFace.FACE_TWO, false)
    val dice5 = Dice(DiceFace.FACE_THREE, false)
    val dice6 = Dice(DiceFace.FACE_THREE, false)

    val dices: MutableLiveData<List<Dice>> = MutableLiveData(listOf(dice1, dice2, dice3, dice4, dice5, dice6))

    val card1 = Card("MedKit", "Get 3 HP", 2, R.drawable.medikit_card)
    val card2 = Card("Close to Victory", "Get 3 WP", 3, R.drawable.winning_points_card)
    val card3 = Card("Feeling generous", "1 more roll for all", 4, R.drawable.more_dice_card)

    val cards: MutableLiveData<List<Card>> = MutableLiveData(listOf(card1,card2,card3))

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

    fun applyCard(position: Int) {
        if (cards.value!![position].title == card1.title) {
            addToPlayerValue(DiceFace.HEALTH, currentPlayerInd.value!!, 3)
        }

        if (cards.value!![position].title == card2.title) {
            addToPlayerValue(DiceFace.FACE_ONE, currentPlayerInd.value!!, 3)
        }

        if (cards.value!![position].title == card3.title) {
            max_number_of_rolls.value = max_number_of_rolls.value!! + 1
        }
    }

    fun canApplyCard(position: Int): Boolean {
        if (cards.value!![position].price <= players.value!![currentPlayerInd.value!!].energy) {
            applyCard(position)
            addToPlayerValue(DiceFace.ENERGY, currentPlayerInd.value!!, -cards.value!![position].price)
            return true
        }
        return false
    }

    //Add the value to the Health, Energy, Winning Point depending on the face to the player at the position in the list
    fun addToPlayerValue(face: DiceFace, position: Int, value: Int) {
        var temp_players = players.value
        if (face == DiceFace.FACE_ONE || face == DiceFace.FACE_TWO || face == DiceFace.FACE_THREE) {
            temp_players!![position].winPoint += value
            if (temp_players!![position].winPoint >= 20) {
                is_game_finished.value = Pair(true, players.value!![position].name)
            }
        }

        //Deal with all the rules related to health
        if (face == DiceFace.HEALTH || face == DiceFace.DAMAGE) {
            //A king can't get HP
            if (position != kingPlayerInd.value || face == DiceFace.DAMAGE)
                temp_players!![position].health += value
            //Death case
            if (temp_players!![position].health <= 0) {
                temp_players!![position].health = 0
                temp_players!![position].isAlive = false
                if (position == kingPlayerInd.value){
                    kingPlayerInd.value = -1
                }
                var player_alive = 0
                for (player in players.value!!){
                    if (player.isAlive)
                        player_alive++
                }
                if (player_alive <= 1)
                    is_game_finished.value = Pair(true, players.value!![currentPlayerInd.value!!].name)
            }
            else if (position == kingPlayerInd.value){
                king_being_attacked.value = true
            }
            //Can't go higher than 10 HP
            if (temp_players!![position].health > 10)
                temp_players!![position].health = 10
        }
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

    //Reset dices selection
    fun resetDices() {
        var temp = dices.value
        for (i in 0..(temp!!.size-1)) {
            temp!![i].isSelected = false
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
            temp_id += 1
            if (temp_id == players.value!!.size)
                temp_id = 0
        }
        currentPlayerInd.postValue(temp_id)
        resetDices()
        rollDices()
        //reset the number of remaining rolls
        remaining_rolls.postValue(max_number_of_rolls.value!! - 1)
    }

    //Put the player as king or propose to the current king if s/he wants to go out
    fun in_out_king() {
        if (kingPlayerInd.value == -1) {
            kingPlayerInd.value = currentPlayerInd.value

        }
    }


    //Apply the effects of the final set of dices of a turn on all the players
    fun applyChangeEndOfRolls(){
        //Keeps track of the number of dices of each type
        val num_map = mutableMapOf(DiceFace.FACE_ONE to 0, DiceFace.FACE_TWO to 0, DiceFace.FACE_THREE to 0, DiceFace.HEALTH to 0, DiceFace.ENERGY to 0, DiceFace.DAMAGE to 0)
        //For now it updates each time a change is made but for now it's enough
        for (dice: Dice in dices.value!!) {
            num_map.put(dice.face, num_map.get(dice.face)!! + 1)
        }
        num_map.forEach { entry ->
            //For the dice faces with numbers
            if (entry.key == DiceFace.FACE_ONE || entry.key == DiceFace.FACE_TWO || entry.key == DiceFace.FACE_THREE) {
                if (entry.value >= 3)
                    addToPlayerValue(entry.key, currentPlayerInd.value!!,  diceFaceToInt(entry.key) + entry.value - 3)
            }
            //For the non number faces
            if (entry.key == DiceFace.DAMAGE) {
                //King attacking
                if (currentPlayerInd.value == kingPlayerInd.value) {
                    for (i in 0..3) {
                        if (i != kingPlayerInd.value) {
                            addToPlayerValue(entry.key, i, diceFaceToInt(entry.key) * entry.value)
                        }
                    }
                }
                //King being attacked
                else {
                    if (kingPlayerInd.value != -1) {
                        addToPlayerValue(entry.key, kingPlayerInd.value!!,  diceFaceToInt(entry.key) * entry.value)
                    }
                }
            }
            //Energy
            if (entry.key == DiceFace.ENERGY)
                addToPlayerValue(entry.key, currentPlayerInd.value!!, entry.value)
        }

    }


}