package com.isd.isd

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlin.random.Random

object Utils {


    private const val TAG = "DM"

    fun showNotification(context: Context, title: String, body: String) {
        val builder = NotificationCompat.Builder(context, "ISD.channel.id")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        // Style
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(title)
            .setBigContentTitle(title)
            .setSummaryText(title)
        builder.setStyle(bigTextStyle)

        val manager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "ISD.channel.id"
            val channel =
                NotificationChannel(channelId, "ISD channel", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        manager.notify(Random(565656).nextInt(), builder.build())

    }
}