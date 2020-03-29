package com.crearo.home

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) {
            return
        }
        val packageName = sbn.packageName
        val tickerText = sbn.notification.tickerText
        val bundle = sbn.notification.extras
        val title = bundle.getString("android.title")
        val text = bundle.getString("android.text")

        
    }

}