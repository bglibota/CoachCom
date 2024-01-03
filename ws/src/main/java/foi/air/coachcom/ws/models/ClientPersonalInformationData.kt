package foi.air.coachcom.ws.models


data class ClientPersonalInformationData(

    val user_id: Int?,
    val first_name: String?,
    val last_name: String?,
    val e_mail: String?,
    val date_of_birth: String?,
    val phone_number: String?,
    val place_of_residence: String?,
    val sex: String?

)

data class ClientPersonalInformationDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)