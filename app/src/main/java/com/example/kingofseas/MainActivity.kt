package com.example.kingofseas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kingofseas.ViewModel.GameViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        println("Hello World")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gameViewModel = GameViewModel()
        gameViewModel.test()
    }
}