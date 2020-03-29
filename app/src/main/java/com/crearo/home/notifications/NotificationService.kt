package com.crearo.home.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import timber.log.Timber

class NotificationService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        Timber.d("Testing this app out")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) {
            return
        }
        val packageName = sbn.packageName
        val tickerText = sbn.notification.tickerText
        val bundle = sbn.notification.extras
        val title = bundle.getString("android.title")
        val text = bundle.getString("android.text")

        Timber.d("New notification: $packageName $sbn")
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Timber.d("Notification removed")
    }

}