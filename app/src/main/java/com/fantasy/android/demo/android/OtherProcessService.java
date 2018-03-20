package com.fantasy.android.demo.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by fantasy on 2018/3/20.
 */

public class OtherProcessService extends Service {
    private static final int MESSAGE_RECEIVE_MSG = 10001;
    private Messenger mServerMessenger = new Messenger(new MyServerHandler(this));

    private static class MyServerHandler extends Handler {
        private Context mContext;
        public MyServerHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_MSG:
                    Bundle bundle = msg.getData();
                    Toast.makeText(mContext, bundle.getString("msg"),
                            Toast.LENGTH_SHORT).show();
                    try {
                        Messenger clientMessenger = msg.replyTo;
                        Message reply = new Message();
                        reply.what = MESSAGE_RECEIVE_MSG;
                        Bundle replayBundle = new Bundle();
                        replayBundle.putString("msg", "Hello I'm  server");
                        reply.setData(replayBundle);
                        clientMessenger.send(reply);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServerMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
