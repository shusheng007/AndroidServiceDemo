package top.ss007.servicedemo.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class LocalService : Service() {

    companion object {
        private val TAG: String = LocalService::class.java.simpleName
    }

    private var quit: Boolean = false
    private var count: Int = 0

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "Service is invoke onBind");
        return LocalBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "Service is invoke onUnbind");
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service is invoke Created");
        val thread = Thread {
            while (quit.not()) {
                Thread.sleep(1000)
                count++
                if (count > 1000) {
                    quit = true
                    break
                }
            }
        }
        thread.start()
    }

    fun getCount(): Int {
        return count
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service is invoke Destroyed");
        quit = true;
    }

    inner class LocalBinder : Binder() {
        fun getService(): LocalService {
            return this@LocalService
        }
    }
}
