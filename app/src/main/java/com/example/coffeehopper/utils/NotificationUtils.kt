package com.example.coffeehopper.utils

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.coffeehopper.BuildConfig
import com.example.coffeehopper.R
import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.example.coffeehopper.presentationlayer.activities.CoffeeDetailsActivity

private const val notificationChannelId = BuildConfig.APPLICATION_ID + ".channel"

fun sendNotification(context: Context, coffeeHop: CoffeeHop) {
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

    // Create an Intent for the activity you want to start
    val resultIntent = Intent(context, CoffeeDetailsActivity::class.java)
    resultIntent.putExtra(CoffeeHop.extraName, coffeeHop)

    // Create the TaskStackBuilder
    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
        // Add the intent, which inflates the back stack
        addNextIntentWithParentStack(resultIntent)
        // Get the PendingIntent containing the entire back stack
        getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    val notification: Notification = NotificationCompat.Builder(context, notificationChannelId)
        .setContentTitle(coffeeHop.name)
        .setSmallIcon(R.drawable.ic_outline_coffee_24)
        .setContentText("Near by ${coffeeHop.name} \n Address:\n${coffeeHop.address1} \n${coffeeHop.city}, ${coffeeHop.state} ${coffeeHop.zip}")
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true).build()

    notificationManager.notify(getUniqueId(), notification)

}

private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())