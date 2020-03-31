package com.crearo.home.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.lifecycle.Observer
import com.crearo.home.Config
import com.crearo.home.api.BroadcastRequest
import com.crearo.home.api.BroadcastResponse
import com.crearo.home.api.GoogleHomeViewModel
import timber.log.Timber

/**
 * Avoid changing the name of this class in production.
 * Apparently Android has a bug that it caches the classname and doesn't clear the list even on reboot
 * https://stackoverflow.com/a/40825389/6844926
 **/
class NotificationService : NotificationListenerService() {

    private lateinit var notificationLog: NotificationLog
    private val googleHomeViewModel = GoogleHomeViewModel()
    private val observer = Observer<BroadcastResponse> {
        Timber.d("Broadcast message with response: $it")
    }

    override fun onCreate() {
        super.onCreate()
        notificationLog = NotificationLog(this)
        googleHomeViewModel.responseLiveData.observeForever(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        googleHomeViewModel.responseLiveData.removeObserver(observer)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null || sbn.notification == null) return
        if (sbn.packageName !in Config.PACKAGES_OF_INTEREST) return

        val speechText = AppSpecificNotificationHandler.handle(sbn)
        notificationLog.addLog(speechText)
        googleHomeViewModel.broadcast(BroadcastRequest(speechText, true, "rish"))
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Timber.d("Notification removed")
    }

}