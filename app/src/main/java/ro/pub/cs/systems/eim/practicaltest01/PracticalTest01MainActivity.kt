package ro.pub.cs.systems.eim.practicaltest01

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.util.Log

class PracticalTest01MainActivity : AppCompatActivity() {

    private var count1 = 0
    private var count2 = 0
    private val REQUEST_CODE = 1 // Codul de cerere pentru activitatea secundară
    private var serviceStatus = Constants.SERVICE_STOPPED // Starea inițială a serviciului


    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA)
            Log.d(Constants.BROADCAST_RECEIVER_TAG, message ?: "No message received")
        }
    }

    private val intentFilter = IntentFilter().apply {
        for (action in Constants.actionTypes) {
            addAction(action)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_main)

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val openSecondaryActivityButton = findViewById<Button>(R.id.navigate_to_secondary_activity_button)
        val startServiceButton = findViewById<Button>(R.id.start_service_button)
        val stopServiceButton = findViewById<Button>(R.id.stop_service_button)

        // Restaurarea stării în cazul în care există o stare anterioară
        if (savedInstanceState != null) {
            count1 = savedInstanceState.getInt("COUNT1", 0)
            count2 = savedInstanceState.getInt("COUNT2", 0)
            textView1.text = count1.toString()
            textView2.text = count2.toString()
        }

        button1.setOnClickListener {
            count1++
            textView1.text = count1.toString()
            checkAndStartService()
        }

        button2.setOnClickListener {
            count2++
            textView2.text = count2.toString()
            checkAndStartService()
        }

        // Invocarea activității secundare
        openSecondaryActivityButton.setOnClickListener {
            val intent = Intent(this, PracticalTest01SecondaryActivity::class.java)
            intent.putExtra("NUMBER_OF_CLICKS", count1 + count2)
            startActivityForResult(intent, REQUEST_CODE)
        }
        startServiceButton.setOnClickListener {
            val serviceIntent = Intent(this, PracticalTest01Service::class.java)
            serviceIntent.putExtra(Constants.FIRST_NUMBER, count1)
            serviceIntent.putExtra(Constants.SECOND_NUMBER, count2)
            startService(serviceIntent)
            Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show()
            serviceStatus = Constants.SERVICE_STARTED
        }

        stopServiceButton.setOnClickListener {
            val serviceIntent = Intent(this, PracticalTest01Service::class.java)
            stopService(serviceIntent)
            Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show()
            serviceStatus = Constants.SERVICE_STOPPED
        }
    }

    private fun checkAndStartService() {
        val sum = count1 + count2
        if (sum > Constants.NUMBER_OF_CLICKS_THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED) {
            val serviceIntent = Intent(this, PracticalTest01Service::class.java)
            serviceIntent.putExtra(Constants.FIRST_NUMBER, count1)
            serviceIntent.putExtra(Constants.SECOND_NUMBER, count2)
            startService(serviceIntent)
            serviceStatus = Constants.SERVICE_STARTED
        }
    }

    override fun onDestroy() {
        val serviceIntent = Intent(this, PracticalTest01Service::class.java)
        stopService(serviceIntent)
        serviceStatus = Constants.SERVICE_STOPPED
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("COUNT1", count1)
        outState.putInt("COUNT2", count2)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count1 = savedInstanceState.getInt("COUNT1", 0)
        count2 = savedInstanceState.getInt("COUNT2", 0)
        findViewById<TextView>(R.id.textView1).text = count1.toString()
        findViewById<TextView>(R.id.textView2).text = count2.toString()
    }

    // Gestionarea rezultatelor din activitatea secundară
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Afișează un Toast pentru rezultatul OK
                Toast.makeText(this, "Result: OK", Toast.LENGTH_SHORT).show()
            } else if (resultCode == RESULT_CANCELED) {
                // Afișează un Toast pentru rezultatul CANCELED
                Toast.makeText(this, "Result: Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
    @SuppressLint("NewApi")
    override fun onResume() {
        super.onResume()
        registerReceiver(
            messageBroadcastReceiver,
            intentFilter,
            RECEIVER_NOT_EXPORTED
        )
    }

    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }
}
