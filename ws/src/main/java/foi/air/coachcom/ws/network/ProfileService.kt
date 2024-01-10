package foi.air.coachcom.ws.network

import foi.air.core.models.UserDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

    @GET("api/users/user")
    fun getUserData(@Query("user_id") userId: Int): Call<UserDataResponse>
}