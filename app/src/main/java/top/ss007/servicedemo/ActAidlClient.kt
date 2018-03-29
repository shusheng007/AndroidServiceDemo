package top.ss007.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_act_aidl_client.*
import top.ss007.servicedemo.services.AidlService

class ActAidlClient : AppCompatActivity() {

    companion object {
        private val TAG=ActAidlClient::class.java.simpleName
    }
    var mService:IMyAidlInterface?=null
    var customService:BookManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_aidl_client)
        btnStartService.setOnClickListener(clickListener)
        btnStartServiceCst.setOnClickListener(clickListener)
        btnStopService.setOnClickListener(clickListener)
        btnGetBooks.setOnClickListener(clickListener)
        btnAddBook.setOnClickListener(clickListener)
    }

    private val clickListener= View.OnClickListener{
        val intent:Intent=Intent(this@ActAidlClient, AidlService::class.java)
        when(it.id)
        {
            R.id.btnStartService->{
                intent.putExtra("type","primary")
                bindService(intent,serviceCon, Context.BIND_AUTO_CREATE)
            }
            R.id.btnStartServiceCst->{
                intent.putExtra("type","custom")
                bindService(intent,serviceConSst, Context.BIND_AUTO_CREATE)
            }
            R.id.btnStopService->{
                try {
                    unbindService(serviceCon)
                    unbindService(serviceConSst)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.btnGetBooks->
            {
                val books =customService?.books.toString()
                Toast.makeText(this@ActAidlClient,books,Toast.LENGTH_LONG).show()
            }
            R.id.btnAddBook->
            {
                customService?.addBook(Book("Kotlin",150))
                val books =customService?.books.toString()
                Toast.makeText(this@ActAidlClient,books,Toast.LENGTH_LONG).show()
            }
            else->{

            }
        }
    }

    private val serviceCon = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Toast.makeText(this@ActAidlClient,"断开连接",Toast.LENGTH_SHORT).show()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService=IMyAidlInterface.Stub.asInterface(service)

            Toast.makeText(this@ActAidlClient,mService?.getHelloMsg("jack"),Toast.LENGTH_SHORT).show()
        }
    }

    private val serviceConSst=object :ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            customService=BookManager.Stub.asInterface(service)
            Toast.makeText(this@ActAidlClient,"绑定成功",Toast.LENGTH_SHORT).show()
        }

    }
}
