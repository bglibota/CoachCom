package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.MeasurementDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MeasurementService {

    @GET("API_V2/users/user/measurements")
    fun getMeasurementData(@Query("user_id") userId: Int): Call<MeasurementDataResponse>
}