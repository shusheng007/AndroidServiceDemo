package top.ss007.servicedemo.services

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import top.ss007.servicedemo.ActMainActivity
import top.ss007.servicedemo.Constants
import top.ss007.servicedemo.R

class ForegroundService : Service() {

    companion object {
        private val TAG=ForegroundService::class.java.simpleName
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createChannelId(){
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // The id of the channel.
        val id = "my_channel_01"
        // The user-visible name of the channel.
        val name = "serviceChannel"
        // The user-visible description of the channel.
        val description = "foreground Service Channel"
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel =NotificationChannel(id, name, importance)
        // Configure the notification channel.
        mChannel.description = description
        mChannel.enableLights(true)
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

        mNotificationManager.createNotificationChannel(mChannel)
    }

    fun createNotification() {
        createChannelId()
        val notificationIntent = Intent(this@ForegroundService, ActMainActivity::class.java)
        notificationIntent.action = Constants.ACTION.MAIN_ACTION
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0)

        val previousIntent = Intent(this@ForegroundService, ForegroundService::class.java)
        previousIntent.action = Constants.ACTION.PREV_ACTION
        val ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0)

        val playIntent = Intent(this@ForegroundService, ForegroundService::class.java)
        playIntent.action = Constants.ACTION.PLAY_ACTION
        val pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0)

        val nextIntent = Intent(this@ForegroundService, ForegroundService::class.java)
        nextIntent.action = Constants.ACTION.NEXT_ACTION
        val pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0)

        val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher)

        val notification = NotificationCompat.Builder(this@ForegroundService,"my_channel_01")
                .setContentTitle("TutorialsFace Music Player")
                .setTicker("TutorialsFace Music Player")
                .setContentText("My song")
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Previous",
                        ppreviousIntent)
                .addAction(android.R.drawable.ic_media_play, "Play",
                        pplayIntent)
                .addAction(android.R.drawable.ic_media_next, "Next",
                        pnextIntent).build()
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Start Foreground Intent ");
            createNotification()
            Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(TAG, "Clicked Previous");
            Toast.makeText(this, "Clicked Previous!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(TAG, "Clicked Play")
            Toast.makeText(this, "Clicked Play!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(TAG, "Clicked Next");
            Toast.makeText(this, "Clicked Next!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    override fun onDestroy() {
        //移除前台服务
        stopForeground(true)
        super.onDestroy()
    }
}
