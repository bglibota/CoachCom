package foi.air.coachcom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import foi.air.core.models.ChangePasswordData
import handlers.ChangePasswordHandler
import handlers.DefaultChangePasswordHandler

class ChangePassword : AppCompatActivity() {

    private val changePasswordHandler: ChangePasswordHandler = DefaultChangePasswordHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val back: ImageView = findViewById(R.id.change_password_back)
        back.setOnClickListener {
            onBackPressed()
        }

        val appContext = applicationContext
        val sharedPrefs = appContext.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("user_id", 0)

        val updatePassword: Button = findViewById(R.id.change_password_updateButton)
        updatePassword.setOnClickListener {
            val currentPasswordEditText: TextInputEditText = findViewById(R.id.change_password_currentPassword)
            val newPasswordEditText: TextInputEditText = findViewById(R.id.change_password_newPassword)
            val repeatedPasswordEditText: TextInputEditText = findViewById(R.id.change_password_confirm)

            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val repeatedPassword = repeatedPasswordEditText.text.toString()

            val changePasswordData = ChangePasswordData(
                user_id = userId,
                current_password = currentPassword,
                new_password = newPassword
            )

            if(newPassword != repeatedPassword){
                Snackbar.make(findViewById(android.R.id.content), "Passwords do not match. Please try again.", Snackbar.LENGTH_LONG).show()
            }else{

                changePasswordHandler.updatePassword(changePasswordData) { success, message, error ->
                    if (success) {
                        val intent = Intent(this@ChangePassword, SuccessfulChange::class.java)
                        intent.putExtra("newText", message)
                        startActivity(intent)
                        finish()
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), error ?: "Unknown error", Snackbar.LENGTH_LONG)
                            .show()
                        Log.d("ChangePassword", error ?: "Unknown error")
                    }
                }
            }


        }

    }
}