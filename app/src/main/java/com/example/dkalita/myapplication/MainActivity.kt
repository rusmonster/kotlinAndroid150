package com.example.dkalita.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : AppCompatActivity() {

	val TAG = "MainActivity"

	var future: ListenableFuture<String>? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val textView = findViewById(R.id.text) as TextView

		future = createFuture().apply {
			subscribe {
				onSuccess {
					Log.i(TAG, "onSuccess: $it")
					textView.setText(it)
				}
				onFailure {
					Log.e(TAG, "onFailure")
				}
			}
		}
	}

	fun createFuture() = Futures.immediateFuture("Hello");
}
