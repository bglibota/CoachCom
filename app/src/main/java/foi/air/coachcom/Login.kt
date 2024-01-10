package foi.air.coachcom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import foi.air.coachcom.models.LoginData
import foi.air.coachcom.models.ResponseLoginData
import foi.air.coachcom.ws.network.LoginService
import foi.air.coachcom.ws.network.NetworkService
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val forgotPassword: TextView = findViewById(R.id.login_forgot_password)

        forgotPassword.setOnClickListener {
            val forgotPasswordDialog = PasswordResetFragment()
            forgotPasswordDialog.show(supportFragmentManager, "forgotPasswordDialog")

        }

        val createNewAccount: Button = findViewById(R.id.register_button)

        createNewAccount.setOnClickListener {
            val registerDialog = RegistrationFragment()
            registerDialog.show(supportFragmentManager, "registerDialog")
        }

        val usernameEditText: EditText = findViewById(R.id.login_username)
        val passwordEditText: EditText = findViewById(R.id.login_password)
        val loginButton: Button = findViewById(R.id.login_button)

        loginButton.setOnClickListener {

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val loginService: LoginService = NetworkService.loginService

            val loginData = foi.air.coachcom.models.LoginData(
                insertedUsername = username,
                insertedPassword = password
            )

            val retrofitData = loginService.loginUser(loginData)

            retrofitData.enqueue(object : Callback<foi.air.coachcom.models.ResponseLoginData> {
                override fun onResponse(
                    call: Call<foi.air.coachcom.models.ResponseLoginData>,
                    response: Response<foi.air.coachcom.models.ResponseLoginData>
                ) {

                    if(response.isSuccessful){
                        val responseData = response.body()
                        val session = responseData?.data
                        val userId = session?.user_id ?: 0
                        val role = session?.role

                        val sharedPrefs = getSharedPreferences("User", Context.MODE_PRIVATE)
                        val editor = sharedPrefs.edit()

                        editor.putString("role", role)
                        editor.putInt("user_id",userId)
                        editor.apply()


                        val intent = Intent(this@Login, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    if(!response.isSuccessful){
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()

                    }

                }

                override fun onFailure(call: Call<foi.air.coachcom.models.ResponseLoginData>, t: Throwable) {
                    Log.d("Login", t.toString())
                    Log.d("Login", "Error")
                    Log.d("Login", t.printStackTrace().toString())
                }
            })


        }



    }

}





