package foi.air.core.models

data class MealPlanData(

    val user_id: Int,
    val day: String,
    val breakfast_picture: ByteArray?,
    val breakfast: String,
    val morning_snack_picture: ByteArray?,
    val morning_snack: String,
    val lunch_picture: ByteArray?,
    val lunch: String,
    val afternoon_snack_picture: ByteArray?,
    val afternoon_snack: String,
    val dinner_picture: ByteArray?,
    val dinner: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealPlanData

        if (user_id != other.user_id) return false
        if (day != other.day) return false
        if (breakfast_picture != null) {
            if (other.breakfast_picture == null) return false
            if (!breakfast_picture.contentEquals(other.breakfast_picture)) return false
        } else if (other.breakfast_picture != null) return false
        if (breakfast != other.breakfast) return false
        if (morning_snack_picture != null) {
            if (other.morning_snack_picture == null) return false
            if (!morning_snack_picture.contentEquals(other.morning_snack_picture)) return false
        } else if (other.morning_snack_picture != null) return false
        if (morning_snack != other.morning_snack) return false
        if (lunch_picture != null) {
            if (other.lunch_picture == null) return false
            if (!lunch_picture.contentEquals(other.lunch_picture)) return false
        } else if (other.lunch_picture != null) return false
        if (lunch != other.lunch) return false
        if (afternoon_snack_picture != null) {
            if (other.afternoon_snack_picture == null) return false
            if (!afternoon_snack_picture.contentEquals(other.afternoon_snack_picture)) return false
        } else if (other.afternoon_snack_picture != null) return false
        if (afternoon_snack != other.afternoon_snack) return false
        if (dinner_picture != null) {
            if (other.dinner_picture == null) return false
            if (!dinner_picture.contentEquals(other.dinner_picture)) return false
        } else if (other.dinner_picture != null) return false
        if (dinner != other.dinner) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user_id
        result = 31 * result + day.hashCode()
        result = 31 * result + (breakfast_picture?.contentHashCode() ?: 0)
        result = 31 * result + breakfast.hashCode()
        result = 31 * result + (morning_snack_picture?.contentHashCode() ?: 0)
        result = 31 * result + morning_snack.hashCode()
        result = 31 * result + (lunch_picture?.contentHashCode() ?: 0)
        result = 31 * result + lunch.hashCode()
        result = 31 * result + (afternoon_snack_picture?.contentHashCode() ?: 0)
        result = 31 * result + afternoon_snack.hashCode()
        result = 31 * result + (dinner_picture?.contentHashCode() ?: 0)
        result = 31 * result + dinner.hashCode()
        return result
    }
}

data class MealPlanDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)