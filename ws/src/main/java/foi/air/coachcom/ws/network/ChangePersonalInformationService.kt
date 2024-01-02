package foi.air.coachcom.ws.network

import retrofit2.Call
import foi.air.coachcom.ws.models.ClientPersonalInformationData
import foi.air.coachcom.ws.models.ClientPersonalInformationDataResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangePersonalInformationService {

    @PATCH("API_V2/users/user/client/update")
    fun savePersonalInformation(@Body dataPersonalInformation : ClientPersonalInformationData): Call<ClientPersonalInformationDataResponse>
}