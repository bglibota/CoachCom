package foi.air.coachcom

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import foi.air.coachcom.ws.models.ChangePasswordData
import foi.air.coachcom.ws.models.ChangePasswordDataResponse
import foi.air.coachcom.ws.models.PhysicalMeasurementData
import foi.air.coachcom.ws.models.PhysicalMeasurementDataResponse
import foi.air.coachcom.ws.network.ChangePasswordService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.PhysicalMeasurementService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val back: ImageView = findViewById(R.id.change_physical_back)
        back.setOnClickListener {
            onBackPressed()
        }

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val updatePassword: Button = findViewById(R.id.change_password_updateButton)
        updatePassword.setOnClickListener {
            val currentPasswordEditText: TextInputEditText = findViewById(R.id.change_password_currentPassword)
            val newPasswordEditText: TextInputEditText = findViewById(R.id.change_password_newPassword)
            val repeatedPasswordEditText: TextInputEditText = findViewById(R.id.change_password_confirm)

            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val repeatedPassword = repeatedPasswordEditText.text.toString()

            val changePasswordData = ChangePasswordData(
                user_id = userId,
                current_password = currentPassword,
                new_password = newPassword
            )

            if(newPassword != repeatedPassword){
                Snackbar.make(findViewById(android.R.id.content), "Passwords do not match. Please try again.", Snackbar.LENGTH_LONG).show()
            }else{

                val changePasswordService: ChangePasswordService = NetworkService.changePasswordService

                val call: Call<ChangePasswordDataResponse> = changePasswordService.updatePassword(changePasswordData)

                call.enqueue(object : Callback<ChangePasswordDataResponse> {
                    override fun onResponse(call: Call<ChangePasswordDataResponse>, response: Response<ChangePasswordDataResponse>) {
                        if (response.isSuccessful) {
                            val responseChangePasswordData = response.body()
                            val message = responseChangePasswordData?.message

                            val intent = Intent(this@ChangePassword, SuccessfulChange::class.java)
                            intent.putExtra("newText", "$message")
                            startActivity(intent)
                            finish()

                        }else{
                            val error = response.errorBody()
                            val errorBody = response.errorBody()?.string()
                            val errorJson = JSONObject(errorBody)
                            val errorMessage = errorJson.optString("message")

                            Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                            Log.d("ChangePassword","$error")
                        }
                    }

                    override fun onFailure(call: Call<ChangePasswordDataResponse>, t: Throwable) {
                        Log.d("ChangePassword","$t")
                    }
                })
            }


        }

    }
}