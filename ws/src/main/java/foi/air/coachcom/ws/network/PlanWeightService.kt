package foi.air.coachcom.ws.network

import foi.air.core.models.PlanWeightDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface PlanWeightService {

    @GET("api/plan_weight")
    fun getPlanWeightData(): Call<PlanWeightDataResponse>

}