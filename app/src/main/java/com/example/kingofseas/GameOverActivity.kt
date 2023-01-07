package com.example.kingofseas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext


class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val intent = intent
        val value = intent.getStringExtra("winner")
        val tv_winner = findViewById<TextView>(R.id.tv_winner)
        tv_winner.text = value + " won"

        val bt_back_menu = findViewById<Button>(R.id.bt_main_menu)
        bt_back_menu.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}