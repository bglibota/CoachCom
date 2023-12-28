package foi.air.coachcom

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import foi.air.coachcom.ws.models.MeasurementDataResponse
import foi.air.coachcom.ws.models.Measurements
import foi.air.coachcom.ws.models.PhysicalMeasurements
import foi.air.coachcom.ws.models.TargetMeasurement
import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class EnterPhysicalMeasurements : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_physical_measurements)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val measurementService: MeasurementService = NetworkService.measurementService

        val call: Call<MeasurementDataResponse> = measurementService.getMeasurementData(userId)

        call.enqueue(object : Callback<MeasurementDataResponse> {
            override fun onResponse(call: Call<MeasurementDataResponse>, response: Response<MeasurementDataResponse>) {
                if (response.isSuccessful) {
                    val responseMeasurementData = response.body()
                    val measurements: Measurements? = responseMeasurementData?.data
                    val targetMeasurements: List<TargetMeasurement>? = measurements?.target_measurements
                    val physicalMeasurements: List<PhysicalMeasurements>? = measurements?.physical_measurements

                    val firstTargetMeasurement: TargetMeasurement? = targetMeasurements?.firstOrNull()
                    val height: Float? = firstTargetMeasurement?.height ?: 0f

                    //val targetWeight: Float? = firstTargetMeasurement?.target_weight ?: 0f
                    //val weights: List<Float> = physicalMeasurements?.map { it.weight } ?: emptyList()

                    val recyclerView: RecyclerView = findViewById(R.id.physical_recyclerView)
                    val physicalMeasurementsAdapter = PhysicalMeasurementsAdapter(physicalMeasurements)
                    recyclerView.adapter = physicalMeasurementsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EnterPhysicalMeasurements)


                }else{
                    val error = response.errorBody()
                    Log.d("EnterPhysicalMeasurements","$error")
                }
            }

            override fun onFailure(call: Call<MeasurementDataResponse>, t: Throwable) {
                Log.d("EnterPhysicalMeasurements","$t")
            }
        })

    }
}

class PhysicalMeasurementsAdapter(private var physicalMeasurements: List<PhysicalMeasurements>?) :
    RecyclerView.Adapter<PhysicalMeasurementsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val containerLayout: LinearLayout = itemView.findViewById(R.id.containerLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measurements_table, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = physicalMeasurements?.get(position)


        if (measurement != null) {
            bindData(holder.containerLayout, measurement)
        }
    }

    override fun getItemCount(): Int {
        return physicalMeasurements?.size ?: 0
    }

    private fun bindData(containerLayout: LinearLayout, measurement: PhysicalMeasurements) {

        containerLayout.removeAllViews()


        addTextViewToContainer(containerLayout, "Weight: ${measurement.weight}")
        addTextViewToContainer(containerLayout, "Waist Circumference: ${measurement.waist_circumference}")
        addTextViewToContainer(containerLayout, "Chest Circumference: ${measurement.chest_circumference}")
        addTextViewToContainer(containerLayout, "Arm Circumference: ${measurement.arm_circumference}")
        addTextViewToContainer(containerLayout, "Leg Circumference: ${measurement.leg_circumference}")
        addTextViewToContainer(containerLayout, "Hip Circumference: ${measurement.hip_circumference}")

        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate: String? = measurement.date?.let { dateFormat.format(it) }
        addTextViewToContainer(containerLayout, "Date: $formattedDate")
    }

    private fun addTextViewToContainer(containerLayout: LinearLayout, text: String) {
        val textView = TextView(containerLayout.context)
        textView.text = text
        textView.setPadding(16, 16, 16, 16)

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        containerLayout.addView(textView, layoutParams)
    }
}
