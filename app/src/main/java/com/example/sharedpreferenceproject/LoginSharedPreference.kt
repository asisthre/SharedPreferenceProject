package com.example.sharedpreferenceproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

class LoginSharedPreference {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATEMODE: Int = 0

    constructor(con: Context) {
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME, PRIVATEMODE)
        editor = pref.edit()
    }

    companion object {
        val PREF_NAME = "Login_Preference"
        val IS_LOGIN = "isLoggedIn"
        val KEY_USERNAME = "UserName"
        val KEY_PASSWORD = "Password"
    }

    fun createLogin(username: String, password: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.commit()
    }

    fun checkLogin() {
        if (this.isLoggedIn()){
            var i = Intent(con, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(i)
        }
    }

    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN, false)
    }

    fun getUserDetails(): HashMap<String, String>{
        var user: Map<String,String> = HashMap<String,String>()
        pref.getString(KEY_USERNAME, null)?.let { (user as HashMap).put(KEY_USERNAME, it) }
        pref.getString(KEY_PASSWORD, null)?.let { (user as HashMap).put(KEY_PASSWORD, it) }
        return user as HashMap<String, String>
    }

    fun logoutUser(){
        editor.clear()
        editor.commit()
        var i : Intent = Intent (con,LoginActivity::class.java )
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(i)
    }

}


