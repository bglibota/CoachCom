package foi.air.core.models

data class PlanWeightDataResponse(
    val success: Boolean,
    val message: String,
    val data: List<PlanDifficulty>
)

data class PlanDifficulty(
    val plan_weight_id: Int,
    val plan_difficulty: String
)
