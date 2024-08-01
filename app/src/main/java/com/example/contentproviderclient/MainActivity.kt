package com.example.contentproviderclient

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity OnCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Activity OnDestroy")
    }
    override fun onResume() {
        super.onResume()
        println("Activity On Resume")
    }

    override fun onPause() {
        super.onPause()
        println("Activity OnPause")
    }

    override fun onStop() {
        super.onStop()
        println("Activity OnStop")
    }

}