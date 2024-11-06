package ro.pub.cs.systems.eim.practicaltest01

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PracticalTest01SecondaryActivity : AppCompatActivity() {

    private lateinit var numberOfClicksTextView: TextView
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    private val buttonClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.ok_button -> setResult(RESULT_OK, null)
            R.id.cancel_button -> setResult(RESULT_CANCELED, null)
        }
        finish()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test01_secondary)

        // Obține referințe la elementele de UI
        numberOfClicksTextView = findViewById(R.id.number_of_clicks_text_view)
        okButton = findViewById(R.id.ok_button)
        cancelButton = findViewById(R.id.cancel_button)

        // Obține datele din intenția care a lansat activitatea
        val intent = intent
        if (intent != null && intent.hasExtra("NUMBER_OF_CLICKS")) {
            val numberOfClicks = intent.getIntExtra("NUMBER_OF_CLICKS", -1)
            numberOfClicksTextView.text = numberOfClicks.toString()
        }

        // Setează listener-ii pentru butoane
        okButton.setOnClickListener(buttonClickListener)
        cancelButton.setOnClickListener(buttonClickListener)
    }
}
