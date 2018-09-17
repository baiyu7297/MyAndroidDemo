package com.fantasy.android.demo.android;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonParcelable implements Parcelable{

    String name;
    int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public PersonParcelable() {
    }

    protected PersonParcelable(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<PersonParcelable> CREATOR = new Creator<PersonParcelable>() {
        @Override
        public PersonParcelable createFromParcel(Parcel source) {
            return new PersonParcelable(source);
        }

        @Override
        public PersonParcelable[] newArray(int size) {
            return new PersonParcelable[size];
        }
    };
}
