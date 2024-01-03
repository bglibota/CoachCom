package foi.air.coachcom.ws.models

import java.util.Date

data class MeasurementDataResponse(
    val success: String,
    val message: String,
    val data: Measurements
)

data class Measurements (

    val target_measurements: List<TargetMeasurement>,
    val physical_measurements: List<PhysicalMeasurements>
)

data class TargetMeasurement(
    val target_measurements_id: Int,
    val user_id: Int,
    val height: Float,
    val target_weight: Float,
    val target_waist_circumference: Float,
    val target_chest_circumference: Float,
    val target_arm_circumference: Float,
    val target_leg_circumference: Float,
    val target_hip_circumference: Float,
    val date: Date,
    val formatted_date: String

)

data class PhysicalMeasurements(
    val physical_measurement_id: Int,
    val user_id: Int,
    val weight: Float,
    val waist_circumference: Float,
    val chest_circumference: Float,
    val arm_circumference: Float,
    val leg_circumference: Float,
    val hip_circumference: Float,
    val date: Date,
    val formatted_date: String
)