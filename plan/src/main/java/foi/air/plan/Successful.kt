package foi.air.plan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Successful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful)

        val messageTextView: TextView = findViewById(R.id.successful_title)
        val newText = intent.getStringExtra("newText")
        messageTextView.text = newText


        val confirmButton: Button = findViewById(R.id.successful_plan_confirmButton)
        confirmButton.setOnClickListener {
            onBackPressed()
        }


    }
}