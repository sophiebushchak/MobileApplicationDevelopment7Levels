package com.example.madlevel2task2

data class Question(
    var questionText: String,
    var isTrue: Boolean
) {
    companion object {
        val QUESTIONS = arrayOf(
            "A 'val' and 'var' are the same.",
            "Mobile Application Developments grants 12 ECTS"
        )
    }
}