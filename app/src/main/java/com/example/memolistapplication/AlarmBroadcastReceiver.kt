package com.example.memolistapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.media.RingtoneManager.TYPE_NOTIFICATION
import com.example.memolistapplication.ui.MainActivity

class AlarmBroadcastReceiver():BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val afterNotifyIntent=Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val requestCode = afterNotifyIntent.getIntExtra("RequestCode", 0)
       val pending=PendingIntent.getActivity(context, requestCode,afterNotifyIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        val nm=context?.getSystemService(NotificationManager::class.java)
        val defaultSoundUri=RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)
        val channel=NotificationChannel("default","たいとる",NotificationManager.IMPORTANCE_DEFAULT).apply {
            description="oioioioioioioioioi"
            canShowBadge()
            setSound(defaultSoundUri,null)
            setShowBadge(true)
        }
        nm?.createNotificationChannel(channel)
        val notification=Notification.Builder(context,"default").apply {
            setContentTitle("たいとる")
            setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            setContentText("message")
            setAutoCancel(true)
            setContentIntent(pending)
            setWhen(System.currentTimeMillis())

        }.build()
        nm?.notify(R.string.app_name,notification)
    }
}