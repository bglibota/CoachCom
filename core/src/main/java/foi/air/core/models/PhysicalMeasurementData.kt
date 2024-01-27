package foi.air.core.models


data class PhysicalMeasurementData(

    val user_id: Int,
    val weight: String,
    val waist_circumference: String,
    val chest_circumference: String,
    val arm_circumference: String,
    val leg_circumference: String,
    val hip_circumference: String

)

data class PhysicalMeasurementDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)