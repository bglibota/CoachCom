package foi.air.coachcom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import foi.air.coachcom.models.ClientPersonalInformationData
import foi.air.coachcom.models.ClientPersonalInformationDataResponse
import foi.air.coachcom.models.UserData
import foi.air.coachcom.models.UserDataResponse
import foi.air.coachcom.ws.network.ChangePersonalInformationService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.ProfileService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ChangePersonalInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_personal_information)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }

        val saveButton: Button = findViewById(R.id.change_personal_saveButton)
        saveButton.setOnClickListener {
            val intent = Intent(this, SuccessfulChange::class.java)
            intent.putExtra("newText", "Your personal information has been successfully changed")
            startActivity(intent)
            finish()
        }

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val profileService: ProfileService = NetworkService.profileService

        val call: Call<foi.air.coachcom.models.UserDataResponse> = profileService.getUserData(userId)

        call.enqueue(object : Callback<foi.air.coachcom.models.UserDataResponse> {
            override fun onResponse(call: Call<foi.air.coachcom.models.UserDataResponse>, response: Response<foi.air.coachcom.models.UserDataResponse>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val user: foi.air.coachcom.models.UserData? = responseData?.data

                    val firstName: TextInputEditText = findViewById(R.id.change_personal_firstName)
                    firstName.setText(user?.first_name)
                    val lastName: TextInputEditText = findViewById(R.id.change_personal_lastName)
                    lastName.setText(user?.last_name)
                    val email: TextInputEditText = findViewById(R.id.change_personal_email)
                    email.setText(user?.e_mail)
                    val birthday: TextInputEditText = findViewById(R.id.change_personal_birthday)
                    birthday.setText(user?.formatted_birthdate)
                    val phone: TextInputEditText = findViewById(R.id.change_personal_phone)
                    phone.setText(user?.phone_number)
                    val residence: TextInputEditText = findViewById(R.id.change_personal_place)
                    residence.setText(user?.place_of_residence)
                    val sex: TextInputEditText = findViewById(R.id.change_personal_sex)
                    sex.setText(user?.sex)

                }else{
                    val error = response.errorBody()
                    Log.d("ChangePersonalInformation","$error")
                }
            }

            override fun onFailure(call: Call<foi.air.coachcom.models.UserDataResponse>, t: Throwable) {
                Log.d("ChangePersonalInformation","$t")
            }
        })

        val birthdayEditText: TextInputEditText = findViewById(R.id.change_personal_birthday)

        birthdayEditText.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
            datePickerBuilder.setSelection(calendar.timeInMillis)

            val datePicker = datePickerBuilder.build()

            datePicker.addOnPositiveButtonClickListener {
                val selectedDate = Date(it)
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate)

                birthdayEditText.setText(formattedDate)
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        val saveChanges: Button = findViewById(R.id.change_personal_saveButton)
        saveChanges.setOnClickListener {
            val firstNameEditText: TextInputEditText = findViewById(R.id.change_personal_firstName)
            val lastNameEditText: TextInputEditText = findViewById(R.id.change_personal_lastName)
            val emailEditText: TextInputEditText = findViewById(R.id.change_personal_email)
            val birthdayEditText: TextInputEditText = findViewById(R.id.change_personal_birthday)
            val phoneEditText: TextInputEditText = findViewById(R.id.change_personal_phone)
            val residenceEditText: TextInputEditText = findViewById(R.id.change_personal_place)
            val sexEditText: TextInputEditText = findViewById(R.id.change_personal_sex)

            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val birthdayString = birthdayEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val residence = residenceEditText.text.toString()
            val sex = sexEditText.text.toString()

            val originalDate = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            val birthday = originalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))



            val changePersonalInformationData =
                foi.air.coachcom.models.ClientPersonalInformationData(
                    user_id = userId,
                    first_name = firstName,
                    last_name = lastName,
                    e_mail = email,
                    date_of_birth = birthday,
                    phone_number = phone,
                    place_of_residence = residence,
                    sex = sex
                )

            val changePersonalInformationService: ChangePersonalInformationService = NetworkService.changePersonalInformationService

            val call: Call<foi.air.coachcom.models.ClientPersonalInformationDataResponse> = changePersonalInformationService.savePersonalInformation(changePersonalInformationData)

            call.enqueue(object : Callback<foi.air.coachcom.models.ClientPersonalInformationDataResponse> {
                override fun onResponse(call: Call<foi.air.coachcom.models.ClientPersonalInformationDataResponse>, response: Response<foi.air.coachcom.models.ClientPersonalInformationDataResponse>) {
                    if (response.isSuccessful) {
                        val responseChangePersonalInformationData = response.body()
                        val message = responseChangePersonalInformationData?.message

                        val intent = Intent(this@ChangePersonalInformation, SuccessfulChange::class.java)
                        intent.putExtra("newText", "$message")
                        startActivity(intent)
                        finish()



                    }else{
                        val error = response.errorBody()
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                        Log.d("ChangePersonalInformation","$error")
                    }
                }

                override fun onFailure(call: Call<foi.air.coachcom.models.ClientPersonalInformationDataResponse>, t: Throwable) {
                    Log.d("ChangePersonalInformation","$t")
                }
            })

        }
    }
}