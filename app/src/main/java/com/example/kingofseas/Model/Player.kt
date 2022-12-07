package com.example.kingofseas.Model

data class Player(var name: String,
                  var health: Int,
                  var winPoint: Int,
                  var energy: Int,
                  var isAlive: Boolean,
                  val cards: List<Card>)