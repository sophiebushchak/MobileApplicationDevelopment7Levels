package com.example.madlevel7task2.tools

import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizAnswer
import com.example.madlevel7task2.model.QuizQuestion
import kotlin.random.Random

class MakeExampleQuiz {
    companion object {
        fun getAQuiz(): Quiz {
            val quiz = Quiz(
                "Trivia Quiz", "Lorem ipsum dolor sit amet," +
                        " consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et " +
                        "dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco" +
                        " laboris nisi ut aliquip ex ea commodo consequat."
            )
            var answer1 = QuizAnswer("2")
            var answer2 = QuizAnswer("5")
            var answer3 = QuizAnswer("7")
            quiz.addQuestion(
                QuizQuestion(
                    "How many colors are there in the rainbow?",
                    listOf(
                        answer1,
                        answer2,
                        answer3
                    ),
                    answer3
                )
            )
            answer1 = QuizAnswer("Mouse")
            answer2 = QuizAnswer("Tiger")
            answer3 = QuizAnswer("Blue Whale")
            quiz.addQuestion(
                QuizQuestion(
                    "What is the biggest animal in the world?",
                    listOf(
                        answer1,
                        answer2,
                        answer3
                    ),
                    answer3
                )
            )
            answer1 = QuizAnswer("Bear")
            answer2 = QuizAnswer("Rabbit")
            answer3 = QuizAnswer("Dragon")
            val answer4 = QuizAnswer("Dog")
            quiz.addQuestion(
                QuizQuestion(
                    "Which of these animals do NOT appear in the Chinese zodiac?",
                    listOf(
                        answer1,
                        answer2,
                        answer3,
                        answer4
                    ),
                    answer1
                )
            )
            return quiz;
        }

        fun getAnotherQuiz(): Quiz {
            val quiz = Quiz(
                "Arithmetic Quiz ${Random.nextInt(100,500)}", "This is a quiz where you are " +
                        "asked some basic arithmetic questions."
            )
            val randomQuestionsAmount = Random.nextInt(7, 10)
            for (i in 1..randomQuestionsAmount) {
                val number1 = Random.nextInt(5, 15)
                val number2 = Random.nextInt(5, 15)
                val questionText = "What is $number1 + $number2?"
                val answers = mutableListOf<QuizAnswer>()
                answers.add(QuizAnswer((number1 + number2).toString()))
                for (i in 1..Random.nextInt(2, 4)) {
                    var number = (number1 + Random.nextInt(1, 4))
                    while (answers.contains(QuizAnswer(number.toString()))) {
                        number += 1;
                    }
                    answers.add(QuizAnswer(number.toString()))
                }
                answers.shuffle()
                quiz.addQuestion(
                    QuizQuestion(
                        questionText,
                        answers.toList(),
                        QuizAnswer((number1 + number2).toString())
                    )
                )
            }
            return quiz;
        }
    }
}