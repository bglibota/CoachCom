package handlers

import android.content.Context
import android.util.Log
import foi.air.core.models.LoginData
import foi.air.core.models.ResponseLoginData
import foi.air.coachcom.ws.network.LoginService
import foi.air.coachcom.ws.network.NetworkService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface LoginHandler {
    fun loginUser(username: String, password: String, callback: (success: Boolean, message: String?) -> Unit)
}

class DefaultLoginHandler(private val context: Context) : LoginHandler {

    override fun loginUser(username: String, password: String, callback: (success: Boolean, message: String?) -> Unit) {
        val loginService: LoginService = NetworkService.loginService

        val loginData = LoginData(
            insertedUsername = username,
            insertedPassword = password
        )

        val retrofitData = loginService.loginUser(loginData)

        retrofitData.enqueue(object : Callback<ResponseLoginData> {
            override fun onResponse(
                call: Call<ResponseLoginData>,
                response: Response<ResponseLoginData>
            ) {

                if (response.isSuccessful) {
                    val responseData = response.body()
                    val session = responseData?.data
                    val userId = session?.user_id ?: 0
                    val role = session?.role

                    val sharedPrefs = context.getSharedPreferences("User", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()

                    editor.putString("role", role)
                    editor.putInt("user_id", userId)
                    editor.apply()

                    callback(true, null)
                }

                if (!response.isSuccessful) {
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    callback(false, errorMessage)
                }
            }

            override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                Log.d("Login", t.toString())
                Log.d("Login", "Error")
                Log.d("Login", t.printStackTrace().toString())

                callback(false, "Error: ${t.message}")
            }
        })
    }
}

