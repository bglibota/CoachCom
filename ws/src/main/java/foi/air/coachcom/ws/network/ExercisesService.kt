package foi.air.coachcom.ws.network

import foi.air.core.models.ExerciseDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExercisesService {

    @GET("api/exercises")
    fun getExercisesData(@Query("user_id") userId: Int): Call<ExerciseDataResponse>

}