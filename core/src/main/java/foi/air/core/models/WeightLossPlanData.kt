package foi.air.core.models

data class WeightLossPlanData(
    val user_id: Int,
    val name: String,
    val description: String,
    val start_date: String,
    val end_date: String,
    val plan_weight_id: Int,
    val exercises: List<Int>
)

data class WeightLossPlanDataResponse(
    val success: String,
    val message: String,
    val data: List<Any>
)