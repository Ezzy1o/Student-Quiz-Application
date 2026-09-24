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
            "What is the capital city of Malaysia?",
            listOf(
                "Johor Bahru",
                "Kuala Lumpur",
                "Putrajaya",
                "Ipoh"
            ),
            1
        ),

        Question(
            "Which is the largest state in Malaysia by land area?",
            listOf(
                "Selangor",
                "Pahang",
                "Sabah",
                "Sarawak"
            ),
            3
        ),

        Question(
            "What is the national flower of Malaysia?",
            listOf(
                "Hibiscus (Bunga Raya)",
                "Orchid",
                "Jasmine",
                "Sunflower"
            ),
            0
        ),

        Question(
            "How many states are there in Malaysia?",
            listOf(
                "11",
                "12",
                "13",
                "14"
            ),
            2
        ),

        Question(
            "Mount Kinabalu is located in which Malaysian state?",
            listOf(
                "Sarawak",
                "Sabah",
                "Perak",
                "Pahang"
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

        binding.tvWelcome.text = "Hi, $studentName 👋"

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