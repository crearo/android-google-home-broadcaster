package com.crearo.home.ui

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.crearo.home.R
import com.crearo.home.api.BroadcastRequest
import com.crearo.home.api.GoogleHomeViewModel
import com.crearo.home.notifications.NotificationLog
import com.crearo.home.notifications.NotificationService

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
        val tvNotifLog: TextView = findViewById(R.id.tv_notif_log)
        tvNotifLog.text = NotificationLog(this).getLog().joinToString("\n")
        tvNotifLog.movementMethod = ScrollingMovementMethod()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_open_notification_settings -> launchNotificationSettings()
        }
        return super.onOptionsItemSelected(item)
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
                val componentName = ComponentName.unflattenFromString(names[i]) ?: continue
                if (TextUtils.equals(pkgName, componentName.packageName)) {
                    if (componentName.shortClassName.substring(1) == NotificationService::class.java.simpleName) {
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

    private fun launchNotificationSettings() {
        startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }

    fun dummyBroadcast(unused: View) {
        val et: EditText = findViewById(R.id.et)
        findViewById<TextView>(R.id.tv_response).text = ""
        googleHomeViewModel.broadcast(
            BroadcastRequest(
                if (et.text.isEmpty()) getString(R.string.hey) else et.text.toString(),
                true
            )
        )
    }
}
