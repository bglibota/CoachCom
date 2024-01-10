package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.ResponseLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/users/login")
    fun loginUser(@Body dataLogin: foi.air.coachcom.ws.models.LoginData): Call<ResponseLoginData>
}