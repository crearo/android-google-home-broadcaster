package com.crearo.home.notifications

import android.content.Context

class NotificationLog(context: Context) {

    private val sharedPrefs = context.getSharedPreferences("notification-log", Context.MODE_PRIVATE)
    private val KEY_NOTIF_LOG = "notifcation_log";
    private val DELIMITER = "[/DELIMITER/]"

    fun getLog(): List<String> {
        return sharedPrefs.getString(KEY_NOTIF_LOG, "")!!.split(DELIMITER).toList();
    }

    fun addLog(ticker: String) {
        val list = getLog().toMutableList().apply { add(0, ticker) }
        sharedPrefs.edit().putString(KEY_NOTIF_LOG, list.joinToString(DELIMITER)).apply()
    }

}