package com.fantasy.android.demo.android;

import android.os.RemoteException;

/**
 * Created by fantasy on 2018/3/22.
 */

public class ICalcImpl extends ICalc.Stub {

    @Override
    public int add(int x, int y) throws RemoteException {
        return x + y;
    }
}
