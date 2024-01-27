package handlers

import android.content.Context
import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.TargetMeasurementService
import foi.air.core.models.MeasurementDataResponse
import foi.air.core.models.Measurements
import foi.air.core.models.TargetMeasurement
import foi.air.core.models.TargetMeasurementData
import foi.air.core.models.TargetMeasurementDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface EnterTargetMeasurementsHandler {
    fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: Measurements?, error: String?) -> Unit)
    fun enterTargetMeasurements(data: TargetMeasurementData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultEnterTargetMeasurementsHandler(private val context: Context) : EnterTargetMeasurementsHandler {
    private val measurementService: MeasurementService = NetworkService.measurementService
    private val targetMeasurementService: TargetMeasurementService = NetworkService.targetMeasurementService

    override fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: Measurements?, error: String?) -> Unit) {
        val call = measurementService.getMeasurementData(userId)

        call.enqueue(object : Callback<MeasurementDataResponse> {
            override fun onResponse(call: Call<MeasurementDataResponse>, response: Response<MeasurementDataResponse>) {
                if (response.isSuccessful) {
                    val responseMeasurementData = response.body()
                    val measurements: Measurements? = responseMeasurementData?.data
                    callback.invoke(true, measurements, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<MeasurementDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }

    override fun enterTargetMeasurements(data: TargetMeasurementData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call = targetMeasurementService.enterTargetMeasurements(data)

        call.enqueue(object : Callback<TargetMeasurementDataResponse> {
            override fun onResponse(call: Call<TargetMeasurementDataResponse>, response: Response<TargetMeasurementDataResponse>) {
                if (response.isSuccessful) {
                    val responseTargetMeasurementData = response.body()
                    val message = responseTargetMeasurementData?.message
                    callback.invoke(true, message, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<TargetMeasurementDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}
