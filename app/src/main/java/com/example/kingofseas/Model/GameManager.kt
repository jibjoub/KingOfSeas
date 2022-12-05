package com.example.kingofseas.Model

object GameManager {
    var players : List<Player> = listOf(Player("player", 1,1,1,true, emptyList()),Player("player2", 1,1,1,true, emptyList()) )
    var currentPlayer = players[0]
    //players: List<Player>, currentPlayer: Player
}