package top.ss007.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_act_messenger_client.*
import top.ss007.servicedemo.services.MessengerService








class ActMessengerClient : AppCompatActivity() {

    companion object {
        private val TAG = ActMessengerClient::class.java.simpleName
    }

    lateinit var  mMessenger: Messenger //与服务端交互的Messenger
    private val mReceivedReplyMsg = Messenger(ReceiverReplyMsgHandler())//处理服务器的应答
    val sb:StringBuilder= StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_act_messenger_client)
        btnStartService.setOnClickListener(clickListener)
        btnStopService.setOnClickListener(clickListener)
        btnSendMsg.setOnClickListener(clickListener)
    }

    fun sayHello(sendMsg:String) {
        // 创建与服务交互的消息实体Message
        val msg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT, 0, 0)
        val bundle = Bundle()
        bundle.putString("send", sendMsg)
        msg.data=bundle

        //把接收服务器端的回复的Messenger通过Message的replyTo参数传递给服务端
        msg.replyTo=mReceivedReplyMsg
        mMessenger.send(msg)

        sb.append("客户端对服务器说："+ sendMsg+"\n")
        tvChatContent.text=sb.toString()
    }

    private val serviceCon = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mMessenger = Messenger(service)
        }
    }

    private val clickListener=View.OnClickListener{
        when(it.id)
        {
            R.id.btnStartService->{
                bindService(Intent(this@ActMessengerClient,MessengerService::class.java),serviceCon, Context.BIND_AUTO_CREATE)
            }
            R.id.btnStopService->{
                unbindService(serviceCon)
            }
            R.id.btnSendMsg->{
                sayHello("你是SB")
            }
            else->{

            }
        }
    }

   inner class ReceiverReplyMsgHandler () : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                //接收服务端回复
                MessengerService.MSG_FROM_CLIENT ->{
                    Log.i(ActMessengerClient.TAG, "receiver message from service:" + msg.data.getString("reply"))
                    this@ActMessengerClient.sb.append(msg.data.getString("reply")+"\n")
                    this@ActMessengerClient.tvChatContent.text=this@ActMessengerClient.sb.toString()
                }
                else -> super.handleMessage(msg)
            }
        }
    }
}
