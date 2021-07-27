package ru.konstantin.myweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<AppCompatButton>(R.id.update_weather)
        val textView1 = findViewById<MaterialTextView>(R.id.text_1)
        val textView2 = findViewById<MaterialTextView>(R.id.text_2)

        button.setOnClickListener(View.OnClickListener {
            textView1.text = "UUUuuuuuuuuuuuuuu"
            textView2.text = "AAAAAaaaaaaaaaa"
            Log.d("!@#","ewdfvdesad")
        })
    }
}