package foi.air.plan

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException
import java.io.InputStream

class MealPlan : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST_BREAKFAST = 1
    private val PICK_IMAGE_REQUEST_MORNING_SNACK = 2
    private val PICK_IMAGE_REQUEST_LUNCH = 3
    private val PICK_IMAGE_REQUEST_AFTERNOON_SNACK = 4
    private val PICK_IMAGE_REQUEST_DINNER = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_plan)

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
