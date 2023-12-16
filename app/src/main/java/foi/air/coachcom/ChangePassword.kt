package foi.air.coachcom

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ChangePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }

    }
}