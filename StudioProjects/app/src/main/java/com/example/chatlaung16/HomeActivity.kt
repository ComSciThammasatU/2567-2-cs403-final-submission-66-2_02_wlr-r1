package com.example.chatlaung16
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val btnPool: Button = findViewById(R.id.btnPool)
        val btnFitness: Button = findViewById(R.id.btnFitness)
        val btnSecurity: Button = findViewById(R.id.btnSecurity)
        val btnPayment: Button = findViewById(R.id.btnPayment)
        val btnContact: Button = findViewById(R.id.btnContact)
        btnPool.setOnClickListener {
            startActivity(Intent(this, PoolActivity::class.java))
        }
        btnFitness.setOnClickListener {
            startActivity(Intent(this, FitnessActivity::class.java))
        }
        btnSecurity.setOnClickListener {
            startActivity(Intent(this, SecurityActivity::class.java))
        }
        btnPayment.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        btnContact.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
    }
}