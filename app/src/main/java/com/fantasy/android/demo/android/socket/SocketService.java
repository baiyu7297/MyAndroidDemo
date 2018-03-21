package com.fantasy.android.demo.android.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fantasy on 2018/3/22.
 */

public class SocketService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private ServerSocket mServerSocket;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mServerSocket = new ServerSocket(1999);

                    while (true) {
                        Socket socket = mServerSocket.accept();
                        InputStream inputStream = socket.getInputStream();
                        byte buffer[] = new byte[1024 * 4];
                        int temp = 0;
                        if ((temp = inputStream.read(buffer)) != -1) {
                            final String show = new String(buffer, 0, temp);
                            new Handler(getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SocketService.this, show
                                            , Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mServerSocket != null) {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
