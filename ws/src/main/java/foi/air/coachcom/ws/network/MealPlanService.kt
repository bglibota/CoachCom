package foi.air.coachcom.ws.network

import foi.air.core.models.MealPlanData
import foi.air.core.models.MealPlanDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MealPlanService {

    @POST("api/meal_plan/create")
    fun enterMealPlan(@Body dataMealPlan: MealPlanData): Call<MealPlanDataResponse>
}