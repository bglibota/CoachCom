package handlers

import android.content.Context
import android.util.Log
import foi.air.coachcom.ws.network.ExercisesService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.PlanWeightService
import foi.air.coachcom.ws.network.WeightLossPlanService
import foi.air.core.models.Exercise
import foi.air.core.models.ExerciseDataResponse
import foi.air.core.models.PlanDifficulty
import foi.air.core.models.PlanWeightDataResponse
import foi.air.core.models.WeightLossPlanData
import foi.air.core.models.WeightLossPlanDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface EnterWeightLossPlanHandler {
    fun getPlanWeightData(callback: (success: Boolean, data: List<PlanDifficulty>?, error: String?) -> Unit)
    fun getExercisesData(userId: Int, callback: (success: Boolean, exercisesList: List<Exercise>?, error: String?) -> Unit)
    fun enterWeightLossPlan(data: WeightLossPlanData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultEnterWeightLossPlanHandler(private val context: Context) : EnterWeightLossPlanHandler {

    private val planWeightService: PlanWeightService = NetworkService.planWeightService
    private val exercisesService: ExercisesService = NetworkService.exercisesService
    private val weightLossPlanService: WeightLossPlanService = NetworkService.weightLossPlanService

    override fun getPlanWeightData(callback: (success: Boolean, data: List<PlanDifficulty>?, error: String?) -> Unit) {
        val call: Call<PlanWeightDataResponse> = planWeightService.getPlanWeightData()

        call.enqueue(object : Callback<PlanWeightDataResponse> {
            override fun onResponse(call: Call<PlanWeightDataResponse>, response: Response<PlanWeightDataResponse>) {
                if (response.isSuccessful) {
                    val responsePlanWeightData = response.body()
                    val data: List<PlanDifficulty>? = responsePlanWeightData?.data

                    callback.invoke(true, data, null)
                } else {
                    val error = response.errorBody()
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    callback.invoke(false, null, errorMessage)
                    Log.d("PlanWeight", "$error")
                }
            }

            override fun onFailure(call: Call<PlanWeightDataResponse>, t: Throwable) {
                callback.invoke(false, null, t.toString())
                Log.d("PlanWeight", "$t")
            }
        })
    }

    override fun getExercisesData(userId: Int, callback: (success: Boolean, exercisesList: List<Exercise>?, error: String?) -> Unit) {
        val call: Call<ExerciseDataResponse> = exercisesService.getExercisesData(userId)

        call.enqueue(object : Callback<ExerciseDataResponse> {
            override fun onResponse(call: Call<ExerciseDataResponse>, response: Response<ExerciseDataResponse>) {
                if (response.isSuccessful) {
                    val responseExercisesData = response.body()
                    val exercisesList: List<Exercise> = responseExercisesData?.data ?: emptyList()

                    callback.invoke(true, exercisesList, null)
                } else {
                    val error = response.errorBody()
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    callback.invoke(false, null, errorMessage)
                    Log.d("Exercises", "$error")
                }
            }

            override fun onFailure(call: Call<ExerciseDataResponse>, t: Throwable) {
                callback.invoke(false, null, t.toString())
                Log.d("Exercises", "$t")
            }
        })
    }

    override fun enterWeightLossPlan(data: WeightLossPlanData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call: Call<WeightLossPlanDataResponse> = weightLossPlanService.enterWeightLossPlan(data)

        call.enqueue(object : Callback<WeightLossPlanDataResponse> {
            override fun onResponse(call: Call<WeightLossPlanDataResponse>, response: Response<WeightLossPlanDataResponse>) {
                if (response.isSuccessful) {
                    val responseWeightLossPlanData = response.body()
                    val message = responseWeightLossPlanData?.message

                    callback.invoke(true, message, null)
                } else {
                    val error = response.errorBody()
                    val errorBody = response.errorBody()?.string()
                    val errorJson = JSONObject(errorBody)
                    val errorMessage = errorJson.optString("message")

                    callback.invoke(false, null, errorMessage)
                    Log.d("WeightLossPlan", "$error")
                }
            }

            override fun onFailure(call: Call<WeightLossPlanDataResponse>, t: Throwable) {
                callback.invoke(false, null, t.toString())
                Log.d("WeightLossPlan", "$t")
            }
        })
    }
}
