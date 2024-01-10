package foi.air.coachcom.ws.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangePasswordService {
    @PATCH("api/users/user/password")
    fun updatePassword(@Body dataPasswords : foi.air.coachcom.models.ChangePasswordData): Call<foi.air.coachcom.models.ChangePasswordDataResponse>
}