package com.fantasy.android.demo.android;

import android.os.RemoteException;

/**
 * Created by fantasy on 2018/3/22.
 */

public class IRectImpl extends IRect.Stub{

    @Override
    public int area(int width, int height) throws RemoteException {
        if (width < 0 || height < 0) return 0;
        return width * height;
    }
}
