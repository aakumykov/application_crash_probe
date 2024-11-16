package com.github.aakumykov.kotlin_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.aakumykov.kotlin_playground.databinding.ActivityMainBinding
import com.github.aakumykov.kotlin_playground.extensions.showAppProperties
import com.github.aakumykov.kotlin_playground.extensions.showToast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val a = null
    private var b: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.messages.observe(this) { messages -> binding.logView.text = messages.joinToString("\n") }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(R.string.app_description)

        binding.appInfoButton.setOnClickListener { showAppProperties() }
        binding.clearLogButton.setOnClickListener { Logger.clear() }

        binding.button1.setOnClickListener { action1() }
        binding.button2.setOnClickListener { action2() }
        binding.button3.setOnClickListener { action3() }
        binding.button4.setOnClickListener { action4() }
    }


    private fun action1() {
        val a: String? = null
        val qwerty = a!!
    }

    private fun action2() {
        b = a!!
    }

    private fun action3() {
        showToast("Привет 3")
    }

    private fun action4() {
        showToast("Привет 4")
    }



    private fun log(text:String) = Logger.d(TAG, text)


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}