package foi.air.coachcom.models

data class TargetMeasurementData(

    val user_id: Int,
    val height: Float,
    val target_weight: Float,
    val target_waist_circumference: Float,
    val target_chest_circumference: Float,
    val target_arm_circumference: Float,
    val target_leg_circumference: Float,
    val target_hip_circumference: Float

)

data class TargetMeasurementDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)