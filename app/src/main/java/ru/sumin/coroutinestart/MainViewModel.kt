package ru.sumin.coroutinestart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d(LOG_TAG, "Started")
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0 until 100_000_000) {
                for (j in 0 until 100) {
                    ensureActive()
                    count++
                }
            }
            Log.d(LOG_TAG, "Finished: ${System.currentTimeMillis() - before}")
        }
        job.invokeOnCompletion {
            Log.d(LOG_TAG, "Coroutine was cancelled. $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }

    companion object {

        private const val LOG_TAG = "MainViewModel"
    }
}
