package foi.air.coachcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ChangePersonalInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_personal_information)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}