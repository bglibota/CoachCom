package com.example.coachcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.coachcom.models.LoginData
import com.example.coachcom.models.ResponseData
import com.example.coachcom.network.ApiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:3000/")
                .build()
                .create(ApiInterface::class.java)

            val loginData = LoginData(insertedUsername = username, insertedPassword = password)

            val retrofitData = retrofitBuilder.loginUser(loginData)

            retrofitData.enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {

                    if(response.isSuccessful){
                        val responseData = response.body()
                        val message = responseData?.message
                        Log.d("Login", "Poruka: $message")
                    }
                    if(!response.isSuccessful){
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")
                        Log.d("Login", "Poruka: $errorMessage")
                    }











                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.d("Login", t.toString())
                    Log.d("Login", "greška")
                    Log.d("Login", t.printStackTrace().toString())
                }
            })


        }



    }

}





