package foi.air.core.models

data class ExerciseDataResponse(
    val success: String,
    val message: String,
    val data: List<Exercise>
)

data class Exercise(
    val exercise_id: Int,
    val user_id: Int,
    val name: String,
    val description: String,
    val category: String,
    val difficulty_level: Int?,
    val video_guide_url: String?,
    val step_by_step_instructions: String?,
    val muscle_group: String?,
    val secondary_muscle_group: String?
)
