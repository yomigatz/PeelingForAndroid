package com.yomigatz.peeling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yomigatz.peeling.colorful.ColorFulActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onColorFul(view: View) {
        startActivity(Intent(this,ColorFulActivity::class.java))
    }
}