package com.example.chatlaung16

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminhomepage)
        val btnPayment = findViewById<Button>(R.id.btnPayment)
        val btnContact = findViewById<Button>(R.id.btnContact)
        btnPayment.setOnClickListener {
            val sheetUrl = "https://docs.google.com/spreadsheets/d/1aLwoc3quBV6TxS0K-u_3yT6osUccLnjBwhJcZSDWVh8/edit?gid=0#gid=0"
            val intent = Intent(Intent.ACTION_VIEW, sheetUrl.toUri())
            startActivity(intent)
        }
        btnContact.setOnClickListener {
            val sheetUrl = "https://docs.google.com/spreadsheets/d/13GyIwae8fLE5703jecDNEy-vEeF-fau0rRX7CkeZpX8/edit?gid=0#gid=0"
            val intent = Intent(Intent.ACTION_VIEW, sheetUrl.toUri())
            startActivity(intent)
        }
    }
}
