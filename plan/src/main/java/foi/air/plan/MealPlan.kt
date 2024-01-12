package foi.air.plan

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import foi.air.coachcom.ws.network.MealPlanService
import foi.air.coachcom.ws.network.NetworkService
import foi.air.core.models.MealPlanData
import foi.air.core.models.MealPlanDataResponse
import foi.air.core.models.PhysicalMeasurementDataResponse
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealPlan : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST_BREAKFAST = 1
    private val PICK_IMAGE_REQUEST_MORNING_SNACK = 2
    private val PICK_IMAGE_REQUEST_LUNCH = 3
    private val PICK_IMAGE_REQUEST_AFTERNOON_SNACK = 4
    private val PICK_IMAGE_REQUEST_DINNER = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_plan)

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val back: ImageView = findViewById(R.id.meal_plan_back)

        back.setOnClickListener {
            onBackPressed()
        }

        val breakfastImageButton: ImageButton = findViewById(R.id.breakfastImageButton)
        val morningSnackImageButton: ImageButton = findViewById(R.id.morningSnackImageButton)
        val lunchImageButton: ImageButton = findViewById(R.id.lunchImageButton)
        val afternoonSnackImageButton: ImageButton = findViewById(R.id.afternoonSnackImageButton)
        val dinnerImageButton: ImageButton = findViewById(R.id.dinnerImageButton)

        breakfastImageButton.setOnClickListener { openGallery(PICK_IMAGE_REQUEST_BREAKFAST) }
        morningSnackImageButton.setOnClickListener { openGallery(PICK_IMAGE_REQUEST_MORNING_SNACK) }
        lunchImageButton.setOnClickListener { openGallery(PICK_IMAGE_REQUEST_LUNCH) }
        afternoonSnackImageButton.setOnClickListener { openGallery(PICK_IMAGE_REQUEST_AFTERNOON_SNACK) }
        dinnerImageButton.setOnClickListener { openGallery(PICK_IMAGE_REQUEST_DINNER) }

        val enterMealPlan : Button = findViewById(R.id.meal_plan_enterButton)

        enterMealPlan.setOnClickListener{
            val dayEditText: TextInputEditText = findViewById(R.id.meal_plan_day)
            val day = dayEditText.text.toString()

            val bitmapBreakfastPicture = getBitmapFromImageButton(breakfastImageButton)
            val byteArrayBreakfastPicture = bitmapBreakfastPicture?.let { convertBitmapToByteArray(it) } ?: null

            val breakfastEditText: TextInputEditText = findViewById(R.id.meal_plan_breakfast)
            val breakfast = breakfastEditText.text.toString()


            val bitmapMorningSnackPicture = getBitmapFromImageButton(morningSnackImageButton)
            val byteArrayMorningSnackPicture = bitmapMorningSnackPicture?.let { convertBitmapToByteArray(it) } ?: null

            val morningSnackEditText: TextInputEditText = findViewById(R.id.meal_plan_morningSnack)
            val morningSnack = morningSnackEditText.text.toString()


            val bitmapLunchPicture = getBitmapFromImageButton(lunchImageButton)
            val byteArrayLunchPicture = bitmapLunchPicture?.let { convertBitmapToByteArray(it) } ?: null

            val lunchEditText: TextInputEditText = findViewById(R.id.meal_plan_lunch)
            val lunch = lunchEditText.text.toString()

            val bitmapAfternoonSnackPicture = getBitmapFromImageButton(afternoonSnackImageButton)
            val byteArrayAfternoonSnackPicture = bitmapAfternoonSnackPicture?.let { convertBitmapToByteArray(it) } ?: null

            val afternoonSnackEditText: TextInputEditText = findViewById(R.id.meal_plan_afternoonSnack)
            val afternoonSnack = afternoonSnackEditText.text.toString()

            val bitmapDinnerPicture = getBitmapFromImageButton(dinnerImageButton)
            val byteArrayDinnerPicture = bitmapDinnerPicture?.let { convertBitmapToByteArray(it) } ?: null

            val dinnerEditText: TextInputEditText = findViewById(R.id.meal_plan_dinner)
            val dinner = dinnerEditText.text.toString()

            Log.d("MealPlan","${byteArrayDinnerPicture.toString()}")

            val mealPlanData = MealPlanData(
                user_id = userId,
                day = day,
                breakfast_picture = byteArrayBreakfastPicture,
                breakfast = breakfast,
                morning_snack_picture = byteArrayMorningSnackPicture,
                morning_snack = morningSnack,
                lunch_picture = byteArrayLunchPicture,
                lunch = lunch,
                afternoon_snack_picture = byteArrayAfternoonSnackPicture,
                afternoon_snack = afternoonSnack,
                dinner_picture = byteArrayDinnerPicture,
                dinner = dinner
            )

            val mealPlanService : MealPlanService = NetworkService.mealPlanService

            val call: Call<MealPlanDataResponse> = mealPlanService.enterMealPlan(mealPlanData)

            call.enqueue(object : Callback<MealPlanDataResponse> {
                override fun onResponse(call: Call<MealPlanDataResponse>, response: Response<MealPlanDataResponse>) {
                    if (response.isSuccessful) {
                        val responseMealPlanData = response.body()
                        val message = responseMealPlanData?.message

                        val intent = Intent(this@MealPlan, Successful::class.java)
                        intent.putExtra("newText", "$message")
                        startActivity(intent)
                        finish()

                    }else{
                        val error = response.errorBody()
                        val errorBody = response.errorBody()?.string()
                        val errorJson = JSONObject(errorBody)
                        val errorMessage = errorJson.optString("message")

                        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show()
                        Log.d("MealPlan","$error")
                    }
                }

                override fun onFailure(call: Call<MealPlanDataResponse>, t: Throwable) {
                    Log.d("MealPlan","$t")
                }
            })

        }
    }

    private fun openGallery(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                when (requestCode) {
                    PICK_IMAGE_REQUEST_BREAKFAST -> {
                        val breakfastImageButton: ImageButton = findViewById(R.id.breakfastImageButton)
                        breakfastImageButton.setImageBitmap(bitmap)

                        val breakfastTextView: TextView = findViewById(R.id.breakfastTextView)
                        breakfastTextView.visibility = View.GONE
                    }

                    PICK_IMAGE_REQUEST_MORNING_SNACK -> {
                        val morningSnackImageButton: ImageButton = findViewById(R.id.morningSnackImageButton)
                        morningSnackImageButton.setImageBitmap(bitmap)

                        val morningSnackTextView: TextView = findViewById(R.id.morningSnackTextView)
                        morningSnackTextView.visibility = View.GONE
                    }

                    PICK_IMAGE_REQUEST_LUNCH -> {
                        val lunchImageButton: ImageButton = findViewById(R.id.lunchImageButton)
                        lunchImageButton.setImageBitmap(bitmap)

                        val lunchTextView: TextView = findViewById(R.id.lunchTextView)
                        lunchTextView.visibility = View.GONE
                    }

                    PICK_IMAGE_REQUEST_AFTERNOON_SNACK -> {
                        val afternoonSnackImageButton: ImageButton = findViewById(R.id.afternoonSnackImageButton)
                        afternoonSnackImageButton.setImageBitmap(bitmap)

                        val afternoonSnackTextView: TextView = findViewById(R.id.afternoonSnackTextView)
                        afternoonSnackTextView.visibility = View.GONE
                    }

                    PICK_IMAGE_REQUEST_DINNER -> {
                        val dinnerImageButton: ImageButton = findViewById(R.id.dinnerImageButton)
                        dinnerImageButton.setImageBitmap(bitmap)

                        val dinnerTextView: TextView = findViewById(R.id.dinnerTextView)
                        dinnerTextView.visibility = View.GONE
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}

private fun getBitmapFromImageButton(imageButton: ImageButton): Bitmap? {
    val drawable = imageButton.drawable
    if (drawable != null) {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    return null
}

private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}