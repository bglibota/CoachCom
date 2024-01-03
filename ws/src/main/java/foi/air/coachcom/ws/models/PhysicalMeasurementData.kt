package foi.air.coachcom.ws.models


data class PhysicalMeasurementData(

    val user_id: Int,
    val weight: Float,
    val waist_circumference: Float,
    val chest_circumference: Float,
    val arm_circumference: Float,
    val leg_circumference: Float,
    val hip_circumference: Float

)

data class PhysicalMeasurementDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)