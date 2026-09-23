package com.example.studentquizapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.studentquizapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val TAG = "Lifecycle_ResultActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        Log.d(TAG, "onCreate Callback invoked")

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_result
        )

        val bundle = intent.extras

        if (bundle != null) {
            val name = bundle.getString("EXTRA_NAME", "Student")
            val score = bundle.getInt("EXTRA_SCORE", 0)

            binding.tvNameResult.text = "Congratulations, $name!"
            binding.tvScore.text = "$score / 100"
        }

        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
            finish()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart Callback invoked") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume Callback invoked") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause Callback invoked") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop Callback invoked") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy Callback invoked") } }
