package foi.air.coachcom.mealplan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import modules.PlanModule


class MealPlanModule(private val context: Context) : PlanModule {
    override fun showPlan() {
        val intent = Intent(context, MealPlan::class.java)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }
}