package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.PhysicalMeasurementData
import foi.air.coachcom.ws.models.PhysicalMeasurementDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhysicalMeasurementService {

    @POST("API_V2/users/user/measurements/physical/create")
    fun enterPhysicalMeasurements(@Body dataPhysicalMeasurements: PhysicalMeasurementData): Call<PhysicalMeasurementDataResponse>
}