package handlers

import foi.air.coachcom.ws.network.MeasurementService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.ProfileService
import foi.air.core.models.MeasurementDataResponse
import foi.air.core.models.Measurements
import foi.air.core.models.PhysicalMeasurements
import foi.air.core.models.TargetMeasurement
import foi.air.core.models.UserData
import foi.air.core.models.UserDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ClientProfileHandler {
    fun getUserData(userId: Int, callback: (success: Boolean, user: UserData?, error: String?) -> Unit)
    fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: MeasurementDataResponse?, error: String?) -> Unit)
}

class DefaultClientProfileHandler : ClientProfileHandler {
    private val profileService: ProfileService = NetworkService.profileService
    private val measurementService: MeasurementService = NetworkService.measurementService

    override fun getUserData(userId: Int, callback: (success: Boolean, user: UserData?, error: String?) -> Unit) {
        val call: Call<UserDataResponse> = profileService.getUserData(userId)

        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val user: UserData? = responseData?.data
                    callback.invoke(true, user, null)
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    callback.invoke(false, null, error)
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }

    override fun getMeasurementData(userId: Int, callback: (success: Boolean, measurements: MeasurementDataResponse?, error: String?) -> Unit) {
        val call: Call<MeasurementDataResponse> = measurementService.getMeasurementData(userId)

        call.enqueue(object : Callback<MeasurementDataResponse> {
            override fun onResponse(call: Call<MeasurementDataResponse>, response: Response<MeasurementDataResponse>) {
                if (response.isSuccessful) {
                    val responseMeasurementData = response.body()
                    val targetMeasurements: List<TargetMeasurement>? = responseMeasurementData?.data?.target_measurements
                    val physicalMeasurements: List<PhysicalMeasurements>? = responseMeasurementData?.data?.physical_measurements
                    callback.invoke(true, responseMeasurementData, null)
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    callback.invoke(false, null, error)
                }
            }

            override fun onFailure(call: Call<MeasurementDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}
