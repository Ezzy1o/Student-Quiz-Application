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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate Callback invoked")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)

        val bundleFromMain = intent.extras
        val studentName = bundleFromMain?.getString("EXTRA_NAME", "Student") ?: "Student"

        binding.tvWelcome.text = "Student: $studentName"

        binding.btnSubmit.setOnClickListener {
            val q1Sel = binding.rgQ1.checkedRadioButtonId
            val q2Sel = binding.rgQ2.checkedRadioButtonId
            val q3Sel = binding.rgQ3.checkedRadioButtonId
            val q4Sel = binding.rgQ4.checkedRadioButtonId
            val q5Sel = binding.rgQ5.checkedRadioButtonId

            // Prompt if student leaves any question unselected
            if (q1Sel == -1 || q2Sel == -1 || q3Sel == -1 || q4Sel == -1 || q5Sel == -1) {
                Toast.makeText(this, "Please select an answer for all 5 questions!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var totalScore = 0

            if (q1Sel == R.id.q1b) totalScore += 20 // Answer: B
            if (q2Sel == R.id.q2c) totalScore += 20 // Answer: C
            if (q3Sel == R.id.q3d) totalScore += 20 // Answer: D
            if (q4Sel == R.id.q4a) totalScore += 20 // Answer: A
            if (q5Sel == R.id.q5b) totalScore += 20 // Answer: B
            val resultBundle = Bundle().apply {
                putString("EXTRA_NAME", studentName)
                putInt("EXTRA_SCORE", totalScore)
            }

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtras(resultBundle)
            }

            startActivity(intent)
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart Callback invoked") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume Callback invoked") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause Callback invoked") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop Callback invoked") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy Callback invoked") }
}
