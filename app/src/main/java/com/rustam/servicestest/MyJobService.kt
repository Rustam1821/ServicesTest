package com.rustam.servicestest

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.PersistableBundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyJobService : JobService() {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")

        val page = params?.extras?.getInt(PAGE) ?: 0
        scope.launch {
                for (i in 0 until 5) {
                    delay(1000)
                    log("Timer $i, page: $page")
                }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
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
        const val JOB_ID = 111
        const val PAGE = "page"

        fun createBundle(page: Int) = PersistableBundle().apply {
            putInt(PAGE, page)
        }
    }


}