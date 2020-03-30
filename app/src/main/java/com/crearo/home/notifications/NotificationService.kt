package com.crearo.home.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.lifecycle.Observer
import com.crearo.home.Config
import com.crearo.home.api.BroadcastRequest
import com.crearo.home.api.BroadcastResponse
import com.crearo.home.api.GoogleHomeViewModel
import timber.log.Timber

class NotificationService : NotificationListenerService() {

    private val googleHomeViewModel = GoogleHomeViewModel()
    private val observer = Observer<BroadcastResponse> {
        Timber.d("Broadcast message with response: $it")
    }

    override fun onCreate() {
        super.onCreate()
        googleHomeViewModel.responseLiveData.observeForever(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        googleHomeViewModel.responseLiveData.removeObserver(observer)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) {
            return
        }
        val packageName = sbn.packageName
        val tickerText = sbn.notification.tickerText.toString()
        val bundle = sbn.notification.extras
        val title = bundle.getString("android.title", "")
        val text = bundle.getString("android.text", "")

        if (packageName in Config.PACKAGES_OF_INTEREST) {
            Timber.d("ticker: $tickerText\ntitle:$title\ntext:$text")
            googleHomeViewModel.broadcast(BroadcastRequest(tickerText, true, "rish"))
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Timber.d("Notification removed")
    }

}