package com.example.sharedpreferenceproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    lateinit var session: LoginSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        session = LoginSharedPreference(this)

        if(session.isLoggedIn()){
            var i : Intent = Intent(applicationContext, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

        etUsername= findViewById(R.id.userNameEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnLogin= findViewById(R.id.loginButton)

        btnLogin.setOnClickListener {
            var username = etUsername.text.toString().trim()
            var password = etPassword.text.toString().trim()

            if(username.isEmpty() && password.isEmpty()){
                session.createLogin(username,password)
                var i: Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            }
            else{
                Toast.makeText(this, "Login Failed, Please Try Again", Toast.LENGTH_SHORT).show()
            }

        }
    }
}