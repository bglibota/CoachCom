package foi.air.coachcom.ws.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeasurementService {

    @GET("api/users/user/measurements")
    fun getMeasurementData(@Query("user_id") userId: Int): Call<foi.air.coachcom.models.MeasurementDataResponse>
}