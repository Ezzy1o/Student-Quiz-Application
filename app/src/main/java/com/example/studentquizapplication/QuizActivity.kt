package com.example.studentquizapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.studentquizapplication.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val TAG = "Lifecycle_QuizActivity"

    data class Question(
        val question: String,
        val answers: List<String>,
        val correctAnswer: Int
    )

    private val questions = listOf(

        Question(
            "What is the main purpose of a firewall?",
            listOf(
                "A) To increase internet speed",
                "B) To protect a network by controlling incoming and outgoing traffic",
                "C) To create a backup of files",
                "D) To remove unused applications"
            ),
            1
        ),

        Question(
            "What is phishing?",
            listOf(
                "A) A method of encrypting files",
                "B) A type of network cable",
                "C) An attempt to trick users into revealing sensitive information",
                "D) A method of creating strong passwords"
            ),
            2
        ),

        Question(
            "Which of the following is the strongest password?",
            listOf(
                "A) password123",
                "B) 12345678",
                "C) admin2026",
                "D) T!g8#Qz2@Lm9"
            ),
            3
        ),

        Question(
            "What is malware?",
            listOf(
                "A) Software designed to harm, disrupt, or gain unauthorized access to a computer system",
                "B) Software used to edit documents",
                "C) A secure type of password",
                "D) A network monitoring device"
            ),
            0
        ),

        Question(
            "What is the main purpose of encryption?",
            listOf(
                "A) To make a computer run faster",
                "B) To convert data into a form that unauthorized users cannot easily understand",
                "C) To delete unnecessary files",
                "D) To increase storage capacity"
            ),
            1
        )
    )

    private var currentQuestion = 0

    // -1 means the question has not been answered yet
    private val selectedAnswers = IntArray(5) { -1 }

    private lateinit var studentName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        Log.d(TAG, "onCreate Callback invoked")

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_quiz
        )

        val bundleFromMain = intent.extras

        studentName =
            bundleFromMain?.getString("EXTRA_NAME", "Student")
                ?: "Student"

        binding.tvWelcome.text = "Student: $studentName"

        showQuestion()

        binding.btnNext.setOnClickListener {

            saveSelectedAnswer()

            if (selectedAnswers[currentQuestion] == -1) {

                Toast.makeText(
                    this,
                    "Please select an answer!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (currentQuestion < questions.size - 1) {

                currentQuestion++
                showQuestion()

            } else {

                submitQuiz()
            }
        }

        binding.btnPrevious.setOnClickListener {

            saveSelectedAnswer()

            if (currentQuestion > 0) {

                currentQuestion--
                showQuestion()
            }
        }
    }

    private fun showQuestion() {

        val question = questions[currentQuestion]

        binding.tvQuestionProgress.text =
            "Question ${currentQuestion + 1} of ${questions.size}"

        binding.progressQuiz.progress = currentQuestion + 1

        binding.tvQuestion.text = question.question

        binding.rbAnswerA.text = question.answers[0]
        binding.rbAnswerB.text = question.answers[1]
        binding.rbAnswerC.text = question.answers[2]
        binding.rbAnswerD.text = question.answers[3]

        binding.rgAnswers.clearCheck()

        when (selectedAnswers[currentQuestion]) {

            0 -> binding.rbAnswerA.isChecked = true
            1 -> binding.rbAnswerB.isChecked = true
            2 -> binding.rbAnswerC.isChecked = true
            3 -> binding.rbAnswerD.isChecked = true
        }

        binding.btnPrevious.isEnabled = currentQuestion > 0

        if (currentQuestion == questions.size - 1) {
            binding.btnNext.text = "Submit Quiz"
        } else {
            binding.btnNext.text = "Next"
        }
    }

    private fun saveSelectedAnswer() {

        selectedAnswers[currentQuestion] =
            when (binding.rgAnswers.checkedRadioButtonId) {

                R.id.rbAnswerA -> 0
                R.id.rbAnswerB -> 1
                R.id.rbAnswerC -> 2
                R.id.rbAnswerD -> 3

                else -> -1
            }
    }

    private fun submitQuiz() {

        var totalScore = 0

        questions.forEachIndexed { index, question ->

            if (selectedAnswers[index] == question.correctAnswer) {
                totalScore += 20
            }
        }

        val resultBundle = Bundle().apply {

            putString("EXTRA_NAME", studentName)
            putInt("EXTRA_SCORE", totalScore)
        }

        val intent =
            Intent(this, ResultActivity::class.java).apply {

                putExtras(resultBundle)
            }

        startActivity(intent)

        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Callback invoked")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Callback invoked")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Callback invoked")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Callback invoked")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Callback invoked")
    }
}