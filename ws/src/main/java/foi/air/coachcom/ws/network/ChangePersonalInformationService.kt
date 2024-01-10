package foi.air.coachcom.ws.network

import foi.air.core.models.ClientPersonalInformationData
import foi.air.core.models.ClientPersonalInformationDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ChangePersonalInformationService {

    @PATCH("api/users/user/client/update")
    fun savePersonalInformation(@Body dataPersonalInformation : ClientPersonalInformationData): Call<ClientPersonalInformationDataResponse>
}