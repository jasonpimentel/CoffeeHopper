package com.example.coffeehopper.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.coffeehopper.BuildConfig
import com.example.coffeehopper.R

private const val notificationChannelId = BuildConfig.APPLICATION_ID + ".channel"

fun sendNotification(context: Context, message: String) {
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (notificationManager.getNotificationChannel(notificationChannelId) == null) {
        // create a notification channel
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            notificationChannelId,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    Notification.Builder(context, notificationChannelId).setContentTitle("CoffeeHopper")
        .setContentText(message)
}