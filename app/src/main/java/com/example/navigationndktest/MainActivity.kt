package com.example.navigationndktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.navigationndktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()


        binding.button.setOnClickListener {
            NavigationTest().run(this.applicationContext)
        }
    }

    /**
     * A native method that is implemented by the 'navigationndktest' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'navigationndktest' library on application startup.
        init {
            System.loadLibrary("navigationndktest")
        }
    }
}