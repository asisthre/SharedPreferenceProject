package com.example.sharedpreferenceproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sharedpreferenceproject.FileConstant.LOG_FILE_NAME

class MainActivity : AppCompatActivity() {
    private lateinit var txtUsername: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnSend: Button
    private lateinit var messageEditText:  EditText
    private lateinit var messageText: String
    lateinit var filePref: FileFunctions

    lateinit var session: LoginSharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        session = LoginSharedPreference(this)
        txtUsername = findViewById(R.id.txtUsername)
        btnLogout = findViewById(R.id.btnLogout)
        btnSend = findViewById(R.id.btnSendMessage)
        messageEditText = findViewById(R.id.messageEditText)

        session.checkLogin()
        var user: HashMap<String,String> = session.getUserDetails()
        var username = user.get(LoginSharedPreference.KEY_USERNAME)
        txtUsername.setText(username)

        btnLogout.setOnClickListener{
            session.logoutUser()
        }

        btnSend.setOnClickListener {
            messageText = messageEditText.text.toString().trim()
            if(messageText.isEmpty()){
                Toast.makeText(this, " Please Enter Message", Toast.LENGTH_SHORT).show()
            }
            else{
                filePref.saveToLog(messageText,this)
            }

        }
    }
}