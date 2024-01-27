package foi.air.core.models

data class TargetMeasurementData(

    val user_id: Int,
    val height: String,
    val target_weight: String,
    val target_waist_circumference: String,
    val target_chest_circumference: String,
    val target_arm_circumference: String,
    val target_leg_circumference: String,
    val target_hip_circumference: String

)

data class TargetMeasurementDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)