package com.example.kingofseas.Model

class Card(title: String, description: String, price: Int, val cardEffect: () -> Unit) {

    fun applycardEffect() {
        cardEffect()
    }
}