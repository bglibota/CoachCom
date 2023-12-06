package foi.air.coachcom.network

import foi.air.coachcom.models.LoginData
import foi.air.coachcom.models.ResponseLoginData
import foi.air.coachcom.models.UserDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("API_V2/users/login")
    fun loginUser(@Body dataLogin: LoginData): Call<ResponseLoginData>

    @GET("API_V2/users/user")
    fun getUserData(@Query("user_id") userId: Int): Call<UserDataResponse>

}