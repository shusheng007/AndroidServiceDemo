package top.ss007.servicedemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.act_foregroud_service.*
import top.ss007.servicedemo.services.ForegroundService

class ActForegroudService : AppCompatActivity() {
    companion object {
        private val TAG=ActForegroudService::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_foregroud_service)
        btnStartForeService.setOnClickListener(clickListener)
        btnStopForeServic.setOnClickListener(clickListener)
    }

    private val clickListener= View.OnClickListener { v->
        when(v.id)
        {
            R.id.btnStartForeService->{
                val foreIntent = Intent(this@ActForegroudService, ForegroundService::class.java)
                foreIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(foreIntent)
            }
            R.id.btnStopForeServic->{
                val foreIntent = Intent(this@ActForegroudService, ForegroundService::class.java)
                foreIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(foreIntent)
            }
            else -> {

            }
        }
    }
}
