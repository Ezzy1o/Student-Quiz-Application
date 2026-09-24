package com.example.studentquizapplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.studentquizapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "Lifecycle_MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        Log.d(TAG, "onCreate Callback invoked")

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        binding.btnStart.setOnClickListener {
            val name = binding.etName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your name!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val bundle = Bundle().apply {
                putString("EXTRA_NAME", name)
            }

            val intent = Intent(this, QuizActivity::class.java).apply {
                putExtras(bundle)
            }

            startActivity(intent)
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart Callback invoked") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume Callback invoked") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause Callback invoked") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop Callback invoked") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy Callback invoked") }
}
