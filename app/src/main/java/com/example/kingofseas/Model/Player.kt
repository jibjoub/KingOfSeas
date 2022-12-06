package com.example.kingofseas.Model

data class Player(var name: String,
                  var health: Int,
                  val winPoint: Int,
                  val energy: Int,
                  val isAlive: Boolean,
                  val cards: List<Card>)