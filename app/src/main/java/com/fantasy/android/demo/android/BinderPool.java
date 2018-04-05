package com.fantasy.android.demo.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by fantasy on 2018/3/22.
 */

public class BinderPool {

    private Context mContext;
    private static IBinderPool mBinderPool;
    private static BinderPool mInstance;

    public static final int BINDER_CODE_RECT = 10001;
    public static final int BINDER_CODE_CALC = 10002;

    public static BinderPool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                mInstance = new BinderPool(context);
            }
        }
        return mInstance;
    }

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    private CountDownLatch mCountDownLatch;
    private void connectBinderPoolService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // countdownlatch
                mCountDownLatch = new CountDownLatch(1);

                Intent binderPoolService = new Intent(mContext, BinderPoolService.class);
                mContext.bindService(binderPoolService, mBinderPoolServiceConnection, Context.BIND_AUTO_CREATE);
                try {
                    mCountDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private ServiceConnection mBinderPoolServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null){
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder iBinder = null;
             switch (binderCode) {
                 case BINDER_CODE_RECT:
                     iBinder = new IRectImpl();
                     break;
                 case BINDER_CODE_CALC:
                     iBinder = new ICalcImpl();
                     break;
                 default:
                     break;
             }
            return iBinder;
        }
    }
}
