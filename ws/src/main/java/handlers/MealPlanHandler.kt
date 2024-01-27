package handlers

import android.content.Context
import foi.air.coachcom.ws.network.MealPlanService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.core.models.MealPlanData
import foi.air.core.models.MealPlanDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface EnterMealPlanHandler {
    fun enterMealPlan(data: MealPlanData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultEnterMealPlanHandler(private val context: Context) : EnterMealPlanHandler {
    private val mealPlanService: MealPlanService = NetworkService.mealPlanService

    override fun enterMealPlan(data: MealPlanData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call: Call<MealPlanDataResponse> = mealPlanService.enterMealPlan(data)

        call.enqueue(object : Callback<MealPlanDataResponse> {
            override fun onResponse(call: Call<MealPlanDataResponse>, response: Response<MealPlanDataResponse>) {
                if (response.isSuccessful) {
                    val responseMealPlanData = response.body()
                    val message = responseMealPlanData?.message
                    callback.invoke(true, message, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<MealPlanDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}
