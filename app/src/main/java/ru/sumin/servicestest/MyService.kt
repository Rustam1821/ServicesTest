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
        val start = intent?.getIntExtra(EXTRA_START, 0) ?: 0

        scope.launch {
            for (i in start until start + 100) {
                delay(1000)
                log("Timer $i")
            }
        }
        stopSelf() // we can stop the service from within, like here
        //or from outside MainActivity -> stopService(MyForegroundService.newIntent(this))
        return START_REDELIVER_INTENT
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

        const val EXTRA_START = "extra_start"
        fun newIntent(context: Context, extraStart: Int) = Intent(context, MyService::class.java).apply {
            putExtra(EXTRA_START, extraStart)
        }

    }
}