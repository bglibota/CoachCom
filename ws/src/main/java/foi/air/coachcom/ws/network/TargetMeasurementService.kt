package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.TargetMeasurementData
import foi.air.coachcom.ws.models.TargetMeasurementDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface TargetMeasurementService {

    @PUT("API_V2/users/user/measurements/target/update")
    fun enterTargetMeasurements(@Body dataTargetMeasurements: TargetMeasurementData): Call<TargetMeasurementDataResponse>
}