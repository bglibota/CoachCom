package foi.air.coachcom.weightlossplan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import modules.PlanModule

class WeightLossPlanModule(private val context: Context) : PlanModule {
    override fun showPlan() {
        val intent = Intent(context, WeightLossPlan::class.java)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }
}