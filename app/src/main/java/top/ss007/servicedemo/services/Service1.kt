package top.ss007.servicedemo.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Service1 : Service() {
    companion object {
        private val TAG = Service1::class.java.simpleName
    }

    //Service 的抽象方法，子类必须实现，虽然在启动模式下无需写具体实现代码
    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            Log.d(TAG, "onStartCommand:" + intent.getStringExtra("param1") + " 开始id: " + startId)
        }
        Log.d(TAG,"Thread id:"+ Thread.currentThread().id+" Thread name:"+Thread.currentThread().name)
        return super.onStartCommand(intent, flags, startId)
    }
}
