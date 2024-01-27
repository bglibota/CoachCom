package handlers

import foi.air.coachcom.ws.network.ChangePersonalInformationService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.coachcom.ws.network.ProfileService
import foi.air.core.models.ClientPersonalInformationData
import foi.air.core.models.ClientPersonalInformationDataResponse
import foi.air.core.models.UserData
import foi.air.core.models.UserDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ChangePersonalInformationHandler {
    fun getUserData(userId: Int, callback: (success: Boolean, user: UserData?, error: String?) -> Unit)
    fun savePersonalInformation(data: ClientPersonalInformationData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultChangePersonalInformationHandler : ChangePersonalInformationHandler {
    private val changePersonalInformationService: ChangePersonalInformationService = NetworkService.changePersonalInformationService
    private val profileService: ProfileService = NetworkService.profileService

    override fun getUserData(userId: Int, callback: (success: Boolean, user: UserData?, error: String?) -> Unit) {
        val call: Call<UserDataResponse> = profileService.getUserData(userId)

        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(call: Call<UserDataResponse>, response: Response<UserDataResponse>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val user: UserData? = responseData?.data
                    callback.invoke(true, user, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }

    override fun savePersonalInformation(data: ClientPersonalInformationData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call: Call<ClientPersonalInformationDataResponse> = changePersonalInformationService.savePersonalInformation(data)

        call.enqueue(object : Callback<ClientPersonalInformationDataResponse> {
            override fun onResponse(call: Call<ClientPersonalInformationDataResponse>, response: Response<ClientPersonalInformationDataResponse>) {
                if (response.isSuccessful) {
                    val responseChangePersonalInformationData = response.body()
                    val message = responseChangePersonalInformationData?.message
                    callback.invoke(true, message, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<ClientPersonalInformationDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}
