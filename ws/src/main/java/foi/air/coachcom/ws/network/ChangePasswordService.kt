package foi.air.coachcom.ws.network

import foi.air.coachcom.ws.models.ChangePasswordData
import foi.air.coachcom.ws.models.ChangePasswordDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangePasswordService {
    @PATCH("api/users/user/password")
    fun updatePassword(@Body dataPasswords : ChangePasswordData): Call<ChangePasswordDataResponse>
}