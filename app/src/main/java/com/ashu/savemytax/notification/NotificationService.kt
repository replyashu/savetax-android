package com.ashu.savemytax.notification

import android.content.Context
import android.content.SharedPreferences
import androidx.core.app.NotificationManagerCompat
import com.ashu.ocotopus.ui.notification.CreateNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService: FirebaseMessagingService() {

    private val sharedpreferences: SharedPreferences by lazy { getSharedPreferences("preference_key", Context.MODE_PRIVATE) }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // handle notification
        val builder = CreateNotification.buildNotification(this, message)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(message.data["key1"]?.toInt()!!, builder.build())
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sharedpreferences.edit().putString("user_token", token).apply()
    }


}