package foi.air.coachcom

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import foi.air.coachcom.ws.models.MeasurementDataResponse
import foi.air.coachcom.ws.models.Measurements
import foi.air.coachcom.ws.models.PhysicalMeasurements
import foi.air.coachcom.ws.models.TargetMeasurement
import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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