package com.ashu.ocotopus.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.ashu.savemytax.R
import com.ashu.savemytax.utils.Keys
import com.google.firebase.messaging.RemoteMessage

object CreateNotification {

    fun buildNotification(
        context: Context,
        remoteMessage: RemoteMessage
    ): NotificationCompat.Builder {
        createNotificationChannel(context)
        return NotificationCompat.Builder(context, Keys.notificationChannel())
            .setSmallIcon(R.drawable.ic_icon)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.data["key1"])
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = Keys.notificationChannel()
            val descriptionText = Keys.notificationChannelDescription()
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Keys.notificationChannel(), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(context, NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}