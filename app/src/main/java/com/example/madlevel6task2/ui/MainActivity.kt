package com.example.madlevel6task2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madlevel6task2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}