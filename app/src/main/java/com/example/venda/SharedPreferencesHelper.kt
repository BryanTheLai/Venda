package com.example.venda

import android.content.Context
import android.content.SharedPreferences

// SharedPreferencesHelper.kt
object SharedPreferencesHelper {
    private const val PREF_SELECTED_ITEM = "selected_item"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    fun setSelectedItem(index: Int) {
        sharedPreferences.edit().putInt(PREF_SELECTED_ITEM, index).apply()
    }

    fun getSelectedItem(): Int {
        return sharedPreferences.getInt(PREF_SELECTED_ITEM, 0) // Default to 0 if not found
    }
}
