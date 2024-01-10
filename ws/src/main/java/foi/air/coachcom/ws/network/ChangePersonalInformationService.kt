package foi.air.coachcom.ws.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangePersonalInformationService {

    @PATCH("api/users/user/client/update")
    fun savePersonalInformation(@Body dataPersonalInformation : foi.air.coachcom.models.ClientPersonalInformationData): Call<foi.air.coachcom.models.ClientPersonalInformationDataResponse>
}