package com.crearo.home.notifications

import android.service.notification.StatusBarNotification
import timber.log.Timber

object AppSpecificNotificationHandler {

    fun handle(sbn: StatusBarNotification): String {
        if (sbn.notification == null || sbn.notification.tickerText == null) {
            return ""
        }
        val packageName = sbn.packageName
        val tickerText = sbn.notification.tickerText.toString()
        val bundle = sbn.notification.extras
        val title = bundle.getString("android.title", "")
        val text = bundle.getString("android.text", "")
        Timber.d("ticker: $tickerText\ntitle:$title\ntext:$text")

        return when (packageName) {
            "com.google.android.apps.dynamite" -> "${title.removePrefix("Chat:")} sent $text"
            "com.google.android.calendar" -> tickerText
            else -> tickerText
        }
    }

}