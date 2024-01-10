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
import foi.air.core.models.PhysicalMeasurements
import foi.air.core.models.TargetMeasurement
import foi.air.core.models.TargetMeasurementData
import foi.air.core.models.TargetMeasurementDataResponse
import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.TargetMeasurementService
import org.json.JSONObject
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

                    val recyclerView: RecyclerView = findViewById(R.id.target_recyclerView)
                    val targetMeasurementsAdapter = TargetMeasurementsAdapter(targetMeasurements)

                    recyclerView.layoutManager = LinearLayoutManager(this@EnterTargetMeasurements)
                    recyclerView.adapter = targetMeasurementsAdapter


                }else{
                    val error = response.errorBody()
                    Log.d("EnterTargetMeasurements","$error")
                }
            }

            override fun onFailure(call: Call<MeasurementDataResponse>, t: Throwable) {
                Log.d("EnterTargetMeasurements","$t")
            }
        })

        val enterData: Button = findViewById(R.id.change_target_enterButton)
        enterData.setOnClickListener {
            val heightEditText: TextInputEditText = findViewById(R.id.change_target_height)
            val targetWeightEditText: TextInputEditText = findViewById(R.id.change_target_weight)
            val targetWaistEditText: TextInputEditText = findViewById(R.id.change_target_waistCircumference)
            val targetChestEditText: TextInputEditText = findViewById(R.id.change_target_chestCircumference)
            val targetArmEditText: TextInputEditText = findViewById(R.id.change_target_armCircumference)
            val targetLegEditText: TextInputEditText = findViewById(R.id.change_target_legCircumference)
            val targetHipEditText: TextInputEditText = findViewById(R.id.change_target_hipCircumference)

            val height = heightEditText.text.toString().toFloat()
            val targetWeight = targetWeightEditText.text.toString().toFloat()
            val targetWaistCircumference = targetWaistEditText.text.toString().toFloat()
            val targetChestCircumference = targetChestEditText.text.toString().toFloat()
            val targetArmCircumference = targetArmEditText.text.toString().toFloat()
            val targetLegCircumference = targetLegEditText.text.toString().toFloat()
            val targetHipCircumference = targetHipEditText.text.toString().toFloat()


            val targetMeasurementsData = TargetMeasurementData(
                user_id = userId,
                height = height,
                target_weight = targetWeight,
                target_waist_circumference = targetWaistCircumference,
                target_chest_circumference = targetChestCircumference,
                target_arm_circumference = targetArmCircumference,
                target_leg_circumference = targetLegCircumference,
                target_hip_circumference = targetHipCircumference
            )

            val targetMeasurementService: TargetMeasurementService = NetworkService.targetMeasurementService

            val call: Call<TargetMeasurementDataResponse> = targetMeasurementService.enterTargetMeasurements(targetMeasurementsData)

            call.enqueue(object : Callback<TargetMeasurementDataResponse> {
                override fun onResponse(call: Call<TargetMeasurementDataResponse>, response: Response<TargetMeasurementDataResponse>) {
                    if (response.isSuccessful) {
                        val responseTargetMeasurementData = response.body()
                        val message = responseTargetMeasurementData?.message

                        val intent = Intent(this@EnterTargetMeasurements, SuccessfulChange::class.java)
                        intent.putExtra("newText", "$message")
                        startActivity(intent)
                        finish()



                    }else{
                        val error = response.errorBody()
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                        Log.d("EnterTargetMeasurements","$error")
                    }
                }

                override fun onFailure(call: Call<TargetMeasurementDataResponse>, t: Throwable) {
                    Log.d("EnterTargetMeasurements","$t")
                }
            })

        }

    }
}

class TargetMeasurementsAdapter(private var targetMeasurements: List<TargetMeasurement>?) :
    RecyclerView.Adapter<TargetMeasurementsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val containerLayout: LinearLayout = itemView.findViewById(R.id.containerLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measurements_table, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measurement = targetMeasurements?.get(position)


        if (measurement != null) {
            bindData(holder.containerLayout, measurement)
        }
    }

    override fun getItemCount(): Int {
        return targetMeasurements?.size ?: 0
    }

    private fun bindData(containerLayout: LinearLayout, measurement: TargetMeasurement) {

        containerLayout.removeAllViews()


        addTextViewToContainer(containerLayout, "Height: ${measurement.height}")
        addTextViewToContainer(containerLayout, "Target Waist Circumference: ${measurement.target_waist_circumference}")
        addTextViewToContainer(containerLayout, "Target Chest Circumference: ${measurement.target_chest_circumference}")
        addTextViewToContainer(containerLayout, "Target Arm Circumference: ${measurement.target_arm_circumference}")
        addTextViewToContainer(containerLayout, "Target Leg Circumference: ${measurement.target_leg_circumference}")
        addTextViewToContainer(containerLayout, "Target Hip Circumference: ${measurement.target_hip_circumference}")
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