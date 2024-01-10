package foi.air.coachcom.ws.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhysicalMeasurementService {

    @POST("api/users/user/measurements/physical/create")
    fun enterPhysicalMeasurements(@Body dataPhysicalMeasurements: foi.air.coachcom.models.PhysicalMeasurementData): Call<foi.air.coachcom.models.PhysicalMeasurementDataResponse>
}