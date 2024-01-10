package foi.air.coachcom.models


data class LoginData(
    val insertedUsername: String,
    val insertedPassword: String
)
data class ResponseLoginData(
    val success: String,
    val message: String,
    val data: Session
)

data class Session(
    val user_id: Int,
    val role: String
)