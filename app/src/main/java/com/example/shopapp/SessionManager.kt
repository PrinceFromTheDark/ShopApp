package com.example.shopapp

import android.content.Context
import android.content.SharedPreferences
import com.example.shopapp.dto.GameDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionManager(context: Context) {

    private val PREF_NAME = "MySessionPrefs"
    private val KEY_AUTH_TOKEN = "authToken"
    private val KEY_USER_ID = "userId"
    private val ITEM_CART_LIST = "itemCartList"
    private val IS_LOGGED_IN = "isLoggedIn"

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPrefs.edit()
    private val gson: Gson = Gson()

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

    public var itemsInCart: ArrayList<GameDTO>
        get() {
            val json = sharedPrefs.getString(ITEM_CART_LIST, null)
            if (json == null) return ArrayList<GameDTO>()
            val type = object : TypeToken<ArrayList<GameDTO>>() {}.type
            return gson.fromJson(json, type)
        }
        set(newValue) {
            val json = gson.toJson(newValue)
            editor.putString(ITEM_CART_LIST, json)
            editor.apply()
        }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }
}