package foi.air.coachcom.ws.network

import foi.air.core.models.WeightLossPlanData
import foi.air.core.models.WeightLossPlanDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WeightLossPlanService{
    @POST("api/weight_loss_plan/create")
    fun enterWeightLossPlan(@Body dataWeightLossPlan: WeightLossPlanData): Call<WeightLossPlanDataResponse>
}