package foi.air.coachcom.ws.network

import foi.air.core.models.PhysicalMeasurementData
import foi.air.core.models.PhysicalMeasurementDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhysicalMeasurementService {

    @POST("api/users/user/measurements/physical/create")
    fun enterPhysicalMeasurements(@Body dataPhysicalMeasurements: PhysicalMeasurementData): Call<PhysicalMeasurementDataResponse>
}