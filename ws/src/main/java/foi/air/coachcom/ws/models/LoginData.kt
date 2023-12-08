package foi.air.coachcom.ws.models


data class LoginData(
    val insertedUsername: String,
    val insertedPassword: String
)
data class ResponseLoginData(
    val success: String,
    val message: String,
    val data: session
)

data class session(
    val user_id: Int,
    val role: String
)