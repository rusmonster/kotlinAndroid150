package com.example.dkalita.myapplication

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

	val handler = Handler()

	val runnable: Runnable = Runnable {
		Log.i("!!!", "Hello")
		handler.postDelayed(runnable, 1000)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val textView = findViewById(R.id.text) as TextView
		textView.text = Constants.title

		handler.post(runnable);
	}
}
