package foi.air.coachcom.models

import java.util.Date

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
    val date_of_birth: Date?,
    val phone_number: String?,
    val place_of_residence: String?,
    val sex: String?,
    val biography: String?,
    val registration_date: String?,
    val last_login_time: String?,
    val number_of_login_attempts: Int?,
    val activation_code: String?,
    val picture: ImageData?,
    val documentation_directory_path: String?,
    val profile_status: Int?,
    val biography_video_path: String?,
    val user_type_id: Int?,
    val raw_password: String?,
    val formatted_birthdate: String?
)

data class ImageData(
    val type: String,
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ImageData) return false

        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}