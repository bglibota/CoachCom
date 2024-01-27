package foi.air.coachcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import foi.air.coachcom.mealplan.MealPlan
import foi.air.coachcom.weightlossplan.WeightLossPlan

class Plan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        val weightLossPlan: Button = findViewById(R.id.plan_weightLoss_button)

        weightLossPlan.setOnClickListener{

            val intent = Intent(this@Plan, WeightLossPlan::class.java)
            startActivity(intent)
            finish()
        }

        val mealPlan: Button = findViewById(R.id.plan_mealPlan)

        mealPlan.setOnClickListener{

            val intent = Intent(this@Plan, MealPlan::class.java)
            startActivity(intent)
            finish()
        }

    }
}