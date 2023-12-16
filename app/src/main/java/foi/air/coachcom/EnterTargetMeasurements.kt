package foi.air.coachcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EnterTargetMeasurements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_target_measurements)

        val back: ImageView = findViewById(R.id.change_target_back)
        back.setOnClickListener {
            onBackPressed()
        }

    }
}