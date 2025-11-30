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

    public var authToken: String?
        get() {
            return sharedPrefs.getString(KEY_AUTH_TOKEN, null)
        }
        set(newValue) {
            if (newValue != authToken) {
                editor.putString(KEY_AUTH_TOKEN, newValue)
                editor.apply() // Or .commit() for synchronous saving
            }
        }

    public var userId: Long
        get() {
            return sharedPrefs.getLong(KEY_USER_ID, -1)
        }
        set(newValue) {
            if (newValue != userId) {
                GlobalVars.userId = newValue
                editor.putLong(KEY_USER_ID, newValue)
                editor.apply()
            }
        }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}