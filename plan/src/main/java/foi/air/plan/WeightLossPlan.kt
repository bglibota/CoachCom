package foi.air.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
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



    }
}