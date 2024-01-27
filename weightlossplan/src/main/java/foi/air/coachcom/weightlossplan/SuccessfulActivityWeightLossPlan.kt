package foi.air.coachcom.weightlossplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SuccessfulActivityWeightLossPlan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successful_weight_loss_plan)

        val messageTextView: TextView = findViewById(R.id.successful_weight_loss_plan_title)
        val newText = intent.getStringExtra("newText")
        messageTextView.text = newText


        val confirmButton: Button = findViewById(R.id.successful_weight_loss_plan_confirmButton)
        confirmButton.setOnClickListener {
            onBackPressed()
        }
    }
}