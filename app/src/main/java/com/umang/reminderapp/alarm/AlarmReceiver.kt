package com.umang.reminderapp.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.umang.reminderapp.R

class AlarmReceiver: BroadcastReceiver() {

    private fun showNotification(context: Context?, message: String, dueDate: String) {

        val notification = context?.let {
            NotificationCompat.Builder(it,"todoItemChannel_ID")
                .setContentText("$message is due on $dueDate")
                .setContentTitle("Memento")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()
        }

        val notificationManager = context?.let { getSystemService(it, NotificationManager::class.java) }
        notificationManager?.notify(1, notification)
    }

    private fun showSubscriptionNotification(context: Context?, message: String) {
        val notification = context?.let {
            NotificationCompat.Builder(it,"Subscription Notifications")
                .setContentText(message)
                .setContentTitle("Memento")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("Alarm Receiver","Alarm Received")

        // Check if User has notification Permission
        val hasNotificationPermission = context?.let { NotificationManagerCompat.from(it).areNotificationsEnabled() }

        val action = intent?.action

        when(action){
            "TodoItem" -> {
                val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
                val dueDate = intent.getStringExtra("EXTRA_DUEDATE") ?: return
                if(hasNotificationPermission == true){
                    showNotification(context, message, dueDate)
                } else {
                    println("No Notification")
                }
            }
            "SubscriptionItem" -> {
                val message = intent.getStringExtra("EXTRA_MESSAGE") ?: return
                if(hasNotificationPermission == true){
                    showSubscriptionNotification(context , message)
                } else {
                    println("No Notification")
                }
            }
        }
    }
}
