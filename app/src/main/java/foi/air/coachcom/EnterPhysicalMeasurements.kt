package foi.air.coachcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EnterPhysicalMeasurements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_physical_measurements)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}