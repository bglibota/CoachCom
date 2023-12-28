package foi.air.coachcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SuccessfulChange : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_change)

        val messageTextView: TextView = findViewById(R.id.successful_change_title)
        val newText = intent.getStringExtra("newText")
        messageTextView.text = newText

        val homeButton: Button = findViewById(R.id.successful_homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val backButton: Button = findViewById(R.id.successful_backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}