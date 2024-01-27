package handlers

import foi.air.coachcom.ws.network.ChangePasswordService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.core.models.ChangePasswordData
import foi.air.core.models.ChangePasswordDataResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ChangePasswordHandler {
    fun updatePassword(data: ChangePasswordData, callback: (success: Boolean, message: String?, error: String?) -> Unit)
}

class DefaultChangePasswordHandler : ChangePasswordHandler {
    private val changePasswordService: ChangePasswordService = NetworkService.changePasswordService

    override fun updatePassword(data: ChangePasswordData, callback: (success: Boolean, message: String?, error: String?) -> Unit) {
        val call: Call<ChangePasswordDataResponse> = changePasswordService.updatePassword(data)

        call.enqueue(object : Callback<ChangePasswordDataResponse> {
            override fun onResponse(call: Call<ChangePasswordDataResponse>, response: Response<ChangePasswordDataResponse>) {
                if (response.isSuccessful) {
                    val responseChangePasswordData = response.body()
                    val message = responseChangePasswordData?.message
                    callback.invoke(true, message, null)
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    val errorJson = JSONObject(errorResponse)
                    val errorMessage = errorJson.optString("message", "Unknown error")
                    callback.invoke(false, null, errorMessage)
                }
            }

            override fun onFailure(call: Call<ChangePasswordDataResponse>, t: Throwable) {
                val error = t.localizedMessage ?: "Unknown error"
                callback.invoke(false, null, error)
            }
        })
    }
}
