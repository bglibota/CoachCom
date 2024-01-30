package foi.air.coachcom


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import foi.air.coachcom.mealplan.MealPlanModule
import foi.air.coachcom.weightlossplan.WeightLossPlanModule
import modules.PlanModule

class Plan : AppCompatActivity() {

    private val weightLossPlanModule: PlanModule = WeightLossPlanModule(this)
    private val mealPlanModule: PlanModule = MealPlanModule(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        val weightLossPlan: Button = findViewById(R.id.plan_weightLoss_button)

        weightLossPlan.setOnClickListener {
            weightLossPlanModule.showPlan()
        }

        val mealPlan: Button = findViewById(R.id.plan_mealPlan)

        mealPlan.setOnClickListener {
            mealPlanModule.showPlan()
        }

    }
}