package foi.air.coachcom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import handlers.DefaultLoginHandler
import handlers.LoginHandler


class Login : AppCompatActivity() {

    private val loginHandler: LoginHandler = DefaultLoginHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val forgotPassword: TextView = findViewById(R.id.login_forgot_password)

        forgotPassword.setOnClickListener {
            val forgotPasswordDialog = PasswordResetFragment()
            forgotPasswordDialog.show(supportFragmentManager, "forgotPasswordDialog")

        }

        val createNewAccount: Button = findViewById(R.id.register_button)

        createNewAccount.setOnClickListener {
            val registerDialog = RegistrationFragment()
            registerDialog.show(supportFragmentManager, "registerDialog")
        }

        val usernameEditText: EditText = findViewById(R.id.login_username)
        val passwordEditText: EditText = findViewById(R.id.login_password)
        val loginButton: Button = findViewById(R.id.login_button)

        loginButton.setOnClickListener {

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            loginHandler.loginUser(username, password) { success, message ->
                if (success) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Snackbar.make(findViewById(android.R.id.content), message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
                }
            }


        }



    }

}





