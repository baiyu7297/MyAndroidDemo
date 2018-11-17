package com.fantasy.android.demo.android.dagger2;

import dagger.Component;

@Component
public interface ActivityComponent {

    void inject(DaggerTestActivity activity);
}
