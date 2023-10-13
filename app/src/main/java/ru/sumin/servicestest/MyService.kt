package ru.sumin.servicestest

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")

        scope.launch {
            for (i in 0..100) {
                delay(1000)
                log("Timer $i")
            }
        }
        log("outside the scope")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        log("onDestroy")
        scope.cancel()
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.d("MyService_TAG", message)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MyService::class.java)

    }
}