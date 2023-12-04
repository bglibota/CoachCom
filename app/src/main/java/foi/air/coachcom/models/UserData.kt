package foi.air.coachcom.models

data class UserDataResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val user_id: Int,
    val username: String,
    val password: String,
    val salt: String?,
    val first_name: String?,
    val last_name: String?,
    val e_mail: String?,
    val date_of_birth: String?,
    val phone_number: String?,
    val place_of_residence: String?,
    val sex: String?,
    val biography: String?,
    val registration_date: String?,
    val last_login_time: String?,
    val number_of_login_attempts: Int?,
    val activation_code: String?,
    val profile_picture_path: String?,
    val documentation_directory_path: String?,
    val profile_status: String?,
    val biography_video_path: String?,
    val user_type_id: Int?,
    val raw_password: String?
)