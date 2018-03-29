package top.ss007.servicedemo

import android.os.Parcel
import android.os.Parcelable

/**
 * Copyright (C) 2018 科技发展有限公司
 * 完全享有此软件的著作权，违者必究
 *
 * @author       ben
 * @modifier
 * @createDate   2018/3/28 16:57
 * @version      1.0
 * @description
 */
data class Book(val name: String,val price:Int):Parcelable {
    private constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeInt(price)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object{
        //will be compiled to a static Java field
       @JvmField val CREATOR =object :Parcelable.Creator<Book> {
            override fun createFromParcel(parcel: Parcel): Book {
                return Book(parcel)
            }

            override fun newArray(size: Int): Array<Book?> {
                return arrayOfNulls(size)
            }
        }
    }
}