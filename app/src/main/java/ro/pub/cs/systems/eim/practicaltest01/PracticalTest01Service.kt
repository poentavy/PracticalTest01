package ro.pub.cs.systems.eim.practicaltest01

import android.app.Service
import android.content.Intent
import android.os.IBinder


class PracticalTest01Service : Service() {

    private var processingThread: ProcessingThread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val firstNumber = intent?.getIntExtra(Constants.FIRST_NUMBER, -1) ?: -1
        val secondNumber = intent?.getIntExtra(Constants.SECOND_NUMBER, -1) ?: -1
        processingThread = ProcessingThread(this, firstNumber, secondNumber)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        processingThread?.stopThread()
    }

}