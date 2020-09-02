package com.example.madlevel1task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madlevel1task2.databinding.ActivityTruthTableBinding

class TruthTableActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTruthTableBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTruthTableBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            giveFeedback()
        }
    }

    private fun countCorrect(): Int {
        var amountCorrect: Int = 0;
        val answerTT = binding.inputColumnRow1.text.toString()
        val answerTF = binding.inputColumnRow2.text.toString()
        val answerFT = binding.inputColumnRow3.text.toString()
        val answerFF = binding.inputColumnRow4.text.toString()
        if (answerTT.equals("T", true)) {
            amountCorrect++
        }
        if (answerTF.equals("F", true)) {
            amountCorrect++
        }
        if (answerFT.equals("F", true)) {
            amountCorrect++
        }
        if (answerFF.equals("F", true)) {
            amountCorrect++
        }
        return amountCorrect
    }

    private fun giveFeedback() {
        Toast.makeText(this,"${countCorrect()} out of 4 correct!", Toast.LENGTH_LONG).show()
    }
}