package foi.air.coachcom.ws.models

data class ChangePasswordData(

    val user_id: Int,
    val current_password: String,
    val new_password: String

)

data class ChangePasswordDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)