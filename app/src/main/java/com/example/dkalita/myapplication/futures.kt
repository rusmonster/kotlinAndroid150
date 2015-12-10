package com.example.dkalita.myapplication

import android.os.Handler
import android.os.Looper
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.Executor

private object MainThreadExecutor : Executor {

	private val handler = Handler(Looper.getMainLooper())

	override fun execute(command: Runnable) {
		if (Thread.currentThread() == Looper.getMainLooper().thread) {
			command.run()
		} else {
			handler.post(command)
		}
	}
}

fun <T> ListenableFuture<T>.subscribe(executor: Executor = MainThreadExecutor, body: Callback<T>.() -> Unit) {
	val callback = CallbackImpl<T>()
	callback.body()
	Futures.addCallback(this, callback, executor)
}

interface Callback<T> {

	fun onSuccess(callback: (T) -> Unit)

	fun onFailure(callback: (Throwable) -> Unit)

	fun onFinish(callback: () -> Unit)
}

private class CallbackImpl<T> : Callback<T>, FutureCallback<T> {

	private var onSuccessCallback: (T) -> Unit = {}

	private var onFailureCallback: (Throwable) -> Unit = {}

	private var onFinishCallback: () -> Unit = {}

	override fun onSuccess(callback: (T) -> Unit) {
		onSuccessCallback = callback
	}

	override fun onFailure(callback: (Throwable) -> Unit) {
		onFailureCallback = callback
	}

	override fun onFinish(callback: () -> Unit) {
		onFinishCallback = callback
	}

	override fun onSuccess(result: T) {
		onSuccessCallback(result)
		onFinishCallback()
	}

	override fun onFailure(throwable: Throwable) {
		onFailureCallback(throwable)
		onFinishCallback()
	}
}
