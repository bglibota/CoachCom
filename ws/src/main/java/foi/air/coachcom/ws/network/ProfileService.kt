package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.UserDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

    @GET("API_V2/users/user")
    fun getUserData(@Query("user_id") userId: Int): Call<UserDataResponse>
}