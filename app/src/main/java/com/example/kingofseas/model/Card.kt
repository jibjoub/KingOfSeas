package com.example.kingofseas.model

class Card(title: String, description: String, price: Int, val cardEffect: () -> Unit) {

    fun applycardEffect() {
        cardEffect()
    }
}