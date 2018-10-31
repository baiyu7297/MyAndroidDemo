package com.fantasy.android.demo.android.dagger2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

public class DaggerTestActivity extends Activity{

    @Inject
    User mUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().build().inject(this);

        mUser.setUserName("WANG");
    }
}
