package foi.air.plan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import foi.air.coachcom.ws.network.ExercisesService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.PlanWeightService
import foi.air.core.models.Exercise
import foi.air.core.models.ExerciseDataResponse
import foi.air.core.models.PlanDifficulty
import foi.air.core.models.PlanWeightDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeightLossPlan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_loss_plan)

        val back: ImageView = findViewById(R.id.weight_loss_plan_back)

        back.setOnClickListener {
            onBackPressed()
        }

        val startDateEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_start_date)

        startDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            datePickerBuilder.setSelection(calendar.timeInMillis)

            val datePicker = datePickerBuilder.build()

            datePicker.addOnPositiveButtonClickListener {
                val selectedDate = Date(it)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)

                startDateEditText.setText(formattedDate)
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        val endDateEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_end_date)

        endDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            datePickerBuilder.setSelection(calendar.timeInMillis)

            val datePicker = datePickerBuilder.build()

            datePicker.addOnPositiveButtonClickListener {
                val selectedDate = Date(it)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)

                endDateEditText.setText(formattedDate)
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        val planWeightService : PlanWeightService = NetworkService.planWeightService

        val call: Call<PlanWeightDataResponse> = planWeightService.getPlanWeightData()

        call.enqueue(object : Callback<PlanWeightDataResponse> {
            override fun onResponse(call: Call<PlanWeightDataResponse>, response: Response<PlanWeightDataResponse>) {
                if (response.isSuccessful) {
                    val responsePlanWeightData = response.body()
                    val data: List<PlanDifficulty>? = responsePlanWeightData?.data

                    val difficultyIdMap = mutableMapOf<String, Int>()
                    val mutableDifficultyList = mutableListOf<String>()

                    data?.forEach { difficulty ->
                        difficultyIdMap[difficulty.plan_difficulty] = difficulty.plan_weight_id
                        mutableDifficultyList.add(difficulty.plan_difficulty)
                    }


                    val adapter = ArrayAdapter(this@WeightLossPlan, android.R.layout.simple_spinner_item, mutableDifficultyList)

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                    val difficultySpinner: Spinner = findViewById(R.id.difficultySpinner)
                    difficultySpinner.adapter = adapter

                }else{
                    val error = response.errorBody()
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                    Log.d("PlanWeight","$error")
                }
            }

            override fun onFailure(call: Call<PlanWeightDataResponse>, t: Throwable) {
                Log.d("PlanWeight","$t")
            }
        })

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val exercisesService : ExercisesService = NetworkService.exercisesService

        val call1: Call<ExerciseDataResponse> = exercisesService.getExercisesData(userId)

        call1.enqueue(object : Callback<ExerciseDataResponse> {
            override fun onResponse(call: Call<ExerciseDataResponse>, response: Response<ExerciseDataResponse>) {
                if (response.isSuccessful) {
                    val responseExercisesData = response.body()
                    val exercisesList: List<Exercise> = responseExercisesData?.data ?: emptyList()

                    val recyclerView: RecyclerView = findViewById(R.id.exercisesRecyclerView)
                    val messageTextView: TextView = findViewById(R.id.weight_loss_plan_no_exercises_message)
                    val markImageView: ImageView = findViewById(R.id.weight_loss_plan_imageView_mark)

                    if (exercisesList.isEmpty()) {

                        messageTextView.visibility = View.VISIBLE
                        markImageView.visibility = View.VISIBLE

                    } else {

                        messageTextView.visibility = View.GONE
                        markImageView.visibility = View.GONE

                        val layoutManager = LinearLayoutManager(this@WeightLossPlan)
                        val adapter = ExerciseAdapter(this@WeightLossPlan, exercisesList)

                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = adapter
                    }

                }else{
                    val error = response.errorBody()
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                    Log.d("Exercises","$error")
                }
            }

            override fun onFailure(call: Call<ExerciseDataResponse>, t: Throwable) {
                Log.d("Exercises","$t")
            }
        })

        val enterWeightLossPlan: Button = findViewById(R.id.weight_loss_plan_enterDataButton)


    }
}

class ExerciseAdapter(private val context: Context, private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]

        holder.nameTextView.text = exercise.name
        holder.descriptionTextView.text = exercise.description
    }


    override fun getItemCount(): Int {
        return exercises.size
    }

}


