package foi.air.coachcom.ws.network

import foi.air.core.models.LoginData
import foi.air.core.models.ResponseLoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("api/users/login")
    fun loginUser(@Body dataLogin: LoginData): Call<ResponseLoginData>
}