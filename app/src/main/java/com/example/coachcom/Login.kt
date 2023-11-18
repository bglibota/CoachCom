package com.example.coachcom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {

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

    }



}

