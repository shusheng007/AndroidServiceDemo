package top.ss007.servicedemo.services

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast


class MessengerService : Service() {
    companion object {
        private val TAG = MessengerService::class.java.simpleName
        val MSG_FROM_CLIENT = 1
    }
    val mMessenger=Messenger(ComingInHandler())//可接受handler 及 IBinder的类型为参数


    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG,"MessengerService onBind")
        Toast.makeText(this@MessengerService,"绑定成功",Toast.LENGTH_SHORT).show()
        return mMessenger.binder
    }

    inner class ComingInHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_FROM_CLIENT -> {
                    val receivedMsg : String = msg.data.getString("send","default_msg")
                    Log.i(TAG, receivedMsg);

                    //回复客户端
                    val client = msg.replyTo
                    //获取回复信息的消息实体
                    val replyMsg = Message.obtain(null, MessengerService.MSG_FROM_CLIENT)
                    val bundle = Bundle()
                    bundle.putString("reply", String.format("服务器回应客户端信息 {%s} 为：%s",receivedMsg,"你们全家都SB"))
                    replyMsg.setData(bundle)
                    //向客户端发送消息
                    try {
                        client.send(replyMsg)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }


}
