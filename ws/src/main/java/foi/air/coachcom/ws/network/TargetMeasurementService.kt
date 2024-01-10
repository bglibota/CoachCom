package foi.air.coachcom.ws.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface TargetMeasurementService {

    @PUT("api/users/user/measurements/target/update")
    fun enterTargetMeasurements(@Body dataTargetMeasurements: foi.air.coachcom.models.TargetMeasurementData): Call<foi.air.coachcom.models.TargetMeasurementDataResponse>
}