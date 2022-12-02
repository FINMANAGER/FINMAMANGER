package com.codehub.finmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class TransactionDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)

        //---back button---
        val backButton: ImageButton = findViewById(R.id.backBtn)
        backButton.setOnClickListener {
            finish()
        }
        //--------
    }
}