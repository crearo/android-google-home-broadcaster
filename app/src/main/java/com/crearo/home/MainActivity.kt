package com.crearo.home

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"

class MainActivity : AppCompatActivity() {

    private lateinit var googleHomeViewModel: GoogleHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvResponse: TextView = findViewById(R.id.tv_response)
        googleHomeViewModel = ViewModelProvider(this).get(GoogleHomeViewModel::class.java)
        googleHomeViewModel.responseLiveData.observe(this, Observer {
            tvResponse.text = it.toString()
        })
    }

    override fun onResume() {
        super.onResume()
        if (!isNotificationServiceEnabled()) {
            showNotificationServiceAlertDialog()
        }
    }

    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     */
    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName) &&
                        cn.shortClassName.substring(1) == NotificationService::class.java.simpleName
                    ) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun showNotificationServiceAlertDialog() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(R.string.notification_listener_service)
        dialog.setMessage(R.string.notification_listener_service_explanation)
        dialog.setPositiveButton(
            android.R.string.yes
        ) { _, _ -> startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)) }
        dialog.create().show()
    }

    fun launchNotificationSettings(unused: View) {
        startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    fun dummyBroadcast(unused: View) {
        googleHomeViewModel.broadcast(BroadcastRequest("Hey Rish", true, "rish"))
    }
}
