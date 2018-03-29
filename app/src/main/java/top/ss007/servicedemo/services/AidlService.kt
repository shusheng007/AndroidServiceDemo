package top.ss007.servicedemo.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import top.ss007.servicedemo.Book
import top.ss007.servicedemo.BookManager
import top.ss007.servicedemo.IMyAidlInterface




class AidlService : Service() {

    companion object {
        private val TAG = AidlService::class.java.simpleName
    }

    //包含Book对象的可变list
    private val mBooks :MutableList<Book> = mutableListOf(Book("C++",20), Book("JAVA",50))

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        if ("primary".equals(intent.getStringExtra("type"))) {
            return primaryBinder
        }else
        {
            return customBinder
        }
    }

    //AIDL原生支持的类型，例如String
    private val primaryBinder= object : IMyAidlInterface.Stub(){
        override fun getHelloMsg(info: String?): String= when(info)
        {
            "ben"-> "hello $info"
            "jack"->"fuck $info"
             else->"I dont know he"
        }
    }

    //AIDL支持的实现了Parcelable接口的类型
    private val customBinder:IBinder =object :BookManager.Stub(){
        override fun getBooks(): MutableList<Book> {
            synchronized(this) {
                Log.e(TAG, "Invoking getBooks() method , now the list is : " + mBooks.toString())
                return mBooks
            }
        }

        override fun addBook(book: Book) {
            synchronized(this) {
                if (!mBooks.contains(book)) {
                    mBooks.add(book)
                }
            }
            Log.e(TAG, "Invoking addBooks() method , now the list is : " + mBooks.toString())
        }
    }
}
