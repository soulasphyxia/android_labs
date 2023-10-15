package com.example.lab6

import android.os.Parcel
import android.os.Parcelable

class Item() : Parcelable{
    var id : Int = 0;
    var kind : String = ""
    var title : String = ""
    var price : Double = 0.0
    var weight : Double = 0.0
    var photo: String = ""
    val info: String
        get() = "$kind $weight кг ($price ₽)"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        kind = parcel.readString() ?: ""
        title = parcel.readString() ?: ""
        weight = parcel.readDouble()
        price = parcel.readDouble()
        photo = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(kind)
        parcel.writeString(title)
        parcel.writeDouble(weight)
        parcel.writeDouble(price)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}