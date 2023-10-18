package com.rustam.servicestest

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentServiceShort : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onStartCommand")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i, page: $page")
        }
    }


    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.d("MyIntentService_TAG", message)
    }

    companion object {

        private const val NAME = "intent_service"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int) = Intent(context, MyIntentServiceShort::class.java).apply {
            putExtra(PAGE, page)
        }
    }
}