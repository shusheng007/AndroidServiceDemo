package top.ss007.servicedemo

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.act_main.*
import top.ss007.servicedemo.services.LocalService
import top.ss007.servicedemo.services.Service1


class ActMainActivity : AppCompatActivity() {

    companion object {
        private val TAG = ActMainActivity::class.java.simpleName
    }

    var localService: LocalService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        btnStartService1.setOnClickListener(clickListener)
        btnStopService1.setOnClickListener(clickListener)
        btnStartServiceB1.setOnClickListener(clickListener)
        btnStopServiceB1.setOnClickListener(clickListener)
        btnShowCounter.setOnClickListener(clickListener)
        btnOpenForegroudAct.setOnClickListener(clickListener)
        btnOpenMessengerAct.setOnClickListener(clickListener)
        btnOpenAidlAct.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { v ->
        val intent = Intent(this@ActMainActivity, Service1::class.java)
        intent.putExtra("param1", "hello server")

        val bindIntent = Intent(this@ActMainActivity, LocalService::class.java)


        when (v.id) {
            R.id.btnStartService1 -> {
                startService(intent)
            }
            R.id.btnStopService1 -> {
                stopService(intent)
            }
            R.id.btnStartServiceB1 -> {
                Log.d(TAG, "绑定调用：bindService");
                bindService(bindIntent, serviceCon, Service.BIND_AUTO_CREATE)
            }
            R.id.btnStopServiceB1 -> {
                Log.d(TAG, "解除绑定调用：unbindService");
                // 解除绑定
                if (localService != null) {
                    localService = null;
                    unbindService(serviceCon);
                }
            }
            R.id.btnShowCounter -> {
                btnShowCounter.text = localService?.getCount().toString()
                Log.d(TAG, "服务中的计数器："+localService?.getCount().toString());
            }
            R.id.btnOpenForegroudAct->{
                startActivity(Intent(this@ActMainActivity,ActForegroudService::class.java))
            }
            R.id.btnOpenMessengerAct->{
                startActivity(Intent(this@ActMainActivity,ActMessengerClient::class.java))
            }
            R.id.btnOpenAidlAct->{
                startActivity(Intent(this@ActMainActivity,ActAidlClient::class.java))
            }
            else -> {

            }
        }
    }

    private val serviceCon: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "绑定成功调用：onServiceConnected "+" "+name?.className)
            // 获取Binder
            val binder = service as LocalService.LocalBinder
            localService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            localService = null
        }
    }

}
