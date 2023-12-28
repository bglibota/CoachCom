package foi.air.coachcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ChangePersonalInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_personal_information)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }

        val saveButton: Button = findViewById(R.id.change_personal_saveButton)
        saveButton.setOnClickListener {
            val intent = Intent(this, SuccessfulChange::class.java)
            intent.putExtra("newText", "Your personal information has been successfully changed")
            startActivity(intent)
            finish()
        }
    }
}