package com.fantasy.android.demo.android.dagger2;

import javax.inject.Inject;

public class User {

    public String userName;

    @Inject
    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
