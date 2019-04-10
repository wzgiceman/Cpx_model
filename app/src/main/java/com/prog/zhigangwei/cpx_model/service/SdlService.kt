package com.prog.zhigangwei.cpx_model.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

/**
 *
 *Describe:
 *
 *Created by zhigang wei
 *on 2019/4/3
 *
 *Company :cpx
 */
class SdlService : Service() {

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(...);
//            Notification serviceNotification = new Notification.Builder(this, *Notification Channel*)
//                    .setContentTitle(...)
//            .setSmallIcon(....)
//            .setLargeIcon(...)
//            .setContentText(...)
//            .setChannelId(channel.getId()).build();
//            startForeground(id, serviceNotification);
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}