package handlers

import android.content.Context
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.NetworkService.measurementService
import foi.air.coachcom.ws.network.PhysicalMeasurementService
import foi.air.core.models.MeasurementDataResponse
import foi.air.core.models.Measurements
import foi.air.core.models.PhysicalMeasurementData
import foi.air.core.models.PhysicalMeasurementDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface EnterPhysicalMeasurementsHandler {
    fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: Measurements?, error: String?) -> Unit)
    fun enterPhysicalMeasurements(data: PhysicalMeasurementData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultEnterPhysicalMeasurementsHandler(private val context: Context) : EnterPhysicalMeasurementsHandler {
    private val physicalMeasurementService: PhysicalMeasurementService = NetworkService.physicalMeasurementService

    override fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: Measurements?, error: String?) -> Unit) {
        val call: Call<MeasurementDataResponse> = measurementService.getMeasurementData(userId)

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

    override fun enterPhysicalMeasurements(data: PhysicalMeasurementData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call: Call<PhysicalMeasurementDataResponse> = physicalMeasurementService.enterPhysicalMeasurements(data)

        call.enqueue(object : Callback<PhysicalMeasurementDataResponse> {
            override fun onResponse(call: Call<PhysicalMeasurementDataResponse>, response: Response<PhysicalMeasurementDataResponse>) {
                if (response.isSuccessful) {
                    val responsePhysicalMeasurementData = response.body()
                    val message = responsePhysicalMeasurementData?.message
                    callback.invoke(true, message, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<PhysicalMeasurementDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}

