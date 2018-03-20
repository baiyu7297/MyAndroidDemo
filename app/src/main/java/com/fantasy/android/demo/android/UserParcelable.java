package com.fantasy.android.demo.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fantasy on 2018/3/18.
 */

public class UserParcelable implements Parcelable{

    String name;
    int age;
    String favorite;


    protected UserParcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
        favorite = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(favorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };
}
