package foi.air.coachcom.ws.network

import foi.air.core.models.TargetMeasurementData
import foi.air.core.models.TargetMeasurementDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface TargetMeasurementService {

    @PUT("api/users/user/measurements/target/update")
    fun enterTargetMeasurements(@Body dataTargetMeasurements: TargetMeasurementData): Call<TargetMeasurementDataResponse>
}