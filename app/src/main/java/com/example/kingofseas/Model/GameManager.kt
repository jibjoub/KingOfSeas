package com.example.kingofseas.Model

object GameManager {

    var dices : List<Dice> = List(6){
        Dice(1, false)
    }
    var players : List<Player> = listOf(Player(0, "player", dices),Player(1, "player2", dices) )
    var currentPlayer = players[0]
    //players: List<Player>, currentPlayer: Player
}