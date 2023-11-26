package foi.air.coachcom.network

import foi.air.coachcom.models.LoginData
import foi.air.coachcom.models.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("API_V2/users/login")
    fun loginUser(@Body dataLogin: LoginData): Call<ResponseData>

}