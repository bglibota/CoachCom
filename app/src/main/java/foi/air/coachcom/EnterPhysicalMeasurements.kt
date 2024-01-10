package foi.air.coachcom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import foi.air.core.models.MeasurementDataResponse
import foi.air.core.models.Measurements
import foi.air.core.models.PhysicalMeasurementData
import foi.air.core.models.PhysicalMeasurementDataResponse
import foi.air.core.models.PhysicalMeasurements
import foi.air.core.models.TargetMeasurement
import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.PhysicalMeasurementService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

                    val recyclerView: RecyclerView = findViewById(R.id.physical_recyclerView)
                    val physicalMeasurementsAdapter = PhysicalMeasurementsAdapter(physicalMeasurements)

                    recyclerView.layoutManager = LinearLayoutManager(this@EnterPhysicalMeasurements)
                    recyclerView.adapter = physicalMeasurementsAdapter


                }else{
                    val error = response.errorBody()
                    Log.d("EnterPhysicalMeasurements","$error")
                }
            }

            override fun onFailure(call: Call<MeasurementDataResponse>, t: Throwable) {
                Log.d("EnterPhysicalMeasurements","$t")
            }
        })

        val enterData: Button = findViewById(R.id.change_physical_enterButton)
        enterData.setOnClickListener {
            val weightEditText: TextInputEditText = findViewById(R.id.change_physical_weight)
            val waistEditText: TextInputEditText = findViewById(R.id.change_physical_waistCircumference)
            val chestEditText: TextInputEditText = findViewById(R.id.change_physical_chestCircumference)
            val armEditText: TextInputEditText = findViewById(R.id.change_physical_armCircumference)
            val legEditText: TextInputEditText = findViewById(R.id.change_physical_legCircumference)
            val hipEditText: TextInputEditText = findViewById(R.id.change_physical_hipCircumference)

            val weight = weightEditText.text.toString().toFloat()
            val waistCircumference = waistEditText.text.toString().toFloat()
            val chestCircumference = chestEditText.text.toString().toFloat()
            val armCircumference = armEditText.text.toString().toFloat()
            val legCircumference = legEditText.text.toString().toFloat()
            val hipCircumference = hipEditText.text.toString().toFloat()


            val physicalMeasurementsData = PhysicalMeasurementData(
                user_id = userId,
                weight = weight,
                waist_circumference = waistCircumference,
                chest_circumference = chestCircumference,
                arm_circumference = armCircumference,
                leg_circumference = legCircumference,
                hip_circumference = hipCircumference
            )

            val physicalMeasurementService: PhysicalMeasurementService = NetworkService.physicalMeasurementService

            val call: Call<PhysicalMeasurementDataResponse> = physicalMeasurementService.enterPhysicalMeasurements(physicalMeasurementsData)

            call.enqueue(object : Callback<PhysicalMeasurementDataResponse> {
                override fun onResponse(call: Call<PhysicalMeasurementDataResponse>, response: Response<PhysicalMeasurementDataResponse>) {
                    if (response.isSuccessful) {
                        val responsePhysicalMeasurementData = response.body()
                        val message = responsePhysicalMeasurementData?.message

                        val intent = Intent(this@EnterPhysicalMeasurements, SuccessfulChange::class.java)
                        intent.putExtra("newText", "$message")
                        startActivity(intent)
                        finish()



                    }else{
                        val error = response.errorBody()
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                        Log.d("EnterPhysicalMeasurements","$error")
                    }
                }

                override fun onFailure(call: Call<PhysicalMeasurementDataResponse>, t: Throwable) {
                    Log.d("EnterPhysicalMeasurements","$t")
                }
            })

        }

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
        addTextViewToContainer(containerLayout, "Date: ${measurement.formatted_date}")
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
