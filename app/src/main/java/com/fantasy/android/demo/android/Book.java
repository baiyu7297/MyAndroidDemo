package com.fantasy.android.demo.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fantasy on 2018/3/21.
 */

public class Book implements Parcelable {

    String name;
    int price;

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
