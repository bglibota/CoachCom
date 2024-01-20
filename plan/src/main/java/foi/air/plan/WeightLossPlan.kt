package foi.air.plan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
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
import foi.air.coachcom.ws.network.WeightLossPlanService
import foi.air.core.models.Exercise
import foi.air.core.models.ExerciseDataResponse
import foi.air.core.models.PlanDifficulty
import foi.air.core.models.PlanWeightDataResponse
import foi.air.core.models.WeightLossPlanData
import foi.air.core.models.WeightLossPlanDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WeightLossPlan : AppCompatActivity() {

    private var selectedPlanWeightId: Int = 0
    private val selectedExerciseIds = mutableListOf<Int>()


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

                    difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selectedDifficulty = data?.get(position)
                            selectedDifficulty?.let {
                                selectedPlanWeightId = it.plan_weight_id
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedPlanWeightId = 0
                        }
                    }

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
                        val adapter = ExerciseAdapter(this@WeightLossPlan, exercisesList, selectedExerciseIds)

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

        enterWeightLossPlan.setOnClickListener{

            val nameEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_name)
            val name = nameEditText.text.toString()

            val descriptionEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_description)
            val description = descriptionEditText.text.toString()

            val startDateEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_start_date)


            val startDateString = startDateEditText.text.toString()

            val originalStartDate: LocalDate = if (startDateString.isNotBlank()) {
                try {
                    LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                } catch (e: DateTimeParseException) {
                    LocalDate.now()
                }
            } else {
                LocalDate.now()
            }

            val startDate: String = originalStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


            val endDateEditText: TextInputEditText = findViewById(R.id.weight_loss_plan_end_date)
            val endDateString = endDateEditText.text.toString()

            val originalEndDate: LocalDate = if (endDateString.isNotBlank()) {
                try {
                    LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                } catch (e: DateTimeParseException) {
                    LocalDate.now()
                }
            } else {
                LocalDate.now()
            }

            val endDate: String = originalEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


            val planDifficulty = selectedPlanWeightId

            val exercisesId = selectedExerciseIds

            val weightLossPlanData = WeightLossPlanData(
                user_id = userId,
                name = name,
                description = description,
                start_date = startDate,
                end_date = endDate,
                plan_weight_id = planDifficulty,
                exercises = exercisesId
            )

            val weightLossPlanService : WeightLossPlanService = NetworkService.weightLossPlanService

            val call2: Call<WeightLossPlanDataResponse> = weightLossPlanService.enterWeightLossPlan(weightLossPlanData)

            call2.enqueue(object : Callback<WeightLossPlanDataResponse> {
                override fun onResponse(call: Call<WeightLossPlanDataResponse>, response: Response<WeightLossPlanDataResponse>) {
                    if (response.isSuccessful) {
                        val responseWeightLossPlanData = response.body()
                        val message = responseWeightLossPlanData?.message

                        val intent = Intent(this@WeightLossPlan, Successful::class.java)
                        intent.putExtra("newText", "$message")
                        startActivity(intent)
                        finish()

                    }else{
                        val error = response.errorBody()
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                        Log.d("WeightLossPlan","$error")
                    }
                }

                override fun onFailure(call: Call<WeightLossPlanDataResponse>, t: Throwable) {
                    Log.d("WeightLossPlan","$t")
                }
            })

        }


    }
}

class ExerciseAdapter(private val context: Context, private val exercises: List<Exercise>, private val selectedExerciseIds: MutableList<Int>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val exerciseCheckBox: CheckBox = itemView.findViewById(R.id.exerciseCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]

        holder.exerciseCheckBox.tag = exercise.exercise_id
        holder.nameTextView.text = exercise.name
        holder.descriptionTextView.text = exercise.description

        holder.exerciseCheckBox.isChecked = selectedExerciseIds.contains(exercise.exercise_id)

        holder.exerciseCheckBox.setOnCheckedChangeListener { _, isChecked ->

            val exerciseId = holder.exerciseCheckBox.tag as Int

            if (isChecked) {

                selectedExerciseIds.add(exerciseId)
            } else {

                selectedExerciseIds.remove(exerciseId)
            }

        }

    }


    override fun getItemCount(): Int {
        return exercises.size
    }

}


