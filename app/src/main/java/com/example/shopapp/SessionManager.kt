package com.example.shopapp

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val PREF_NAME = "MySessionPrefs"
    private val KEY_AUTH_TOKEN = "authToken"
    private val KEY_USER_ID = "userId"
    private val IS_LOGGED_IN = "isLoggedIn"

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPrefs.edit()

    fun saveAuthToken(token: String) {
        editor.putString(KEY_AUTH_TOKEN, token)
        editor.apply() // Or .commit() for synchronous saving
    }

    fun getAuthToken(): String? {
        return sharedPrefs.getString(KEY_AUTH_TOKEN, null)
    }

    fun saveUserId(userId: String) {
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPrefs.getString(KEY_USER_ID, null)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, loggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPrefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}