package foi.air.plan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Plan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan)

        val mealPlan: Button = findViewById(R.id.plan_mealPlan)

        mealPlan.setOnClickListener{

            val intent = Intent(this@Plan, MealPlan::class.java)
            startActivity(intent)
            finish()
        }
    }
}