package com.example.chatlaung16

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poolinfo)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }
}