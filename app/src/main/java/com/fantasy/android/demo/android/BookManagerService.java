package com.fantasy.android.demo.android;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fantasy on 2018/3/21.
 */

public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private List<Book> mBookList = new ArrayList<>();
    //private CopyOnWriteArrayList<IOnNewBookArrived> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrived> mListenerList = new RemoteCallbackList<>();

    private IBinder mBinder = new IBookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
            int N = mListenerList.beginBroadcast();
            for (int i = 0; i < N; i++) {
                mListenerList.getBroadcastItem(i).onNewBookArrived(book);
            }
            mListenerList.finishBroadcast();
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void registerOnNewBookArrived(IOnNewBookArrived onNewBookArrived) throws RemoteException {
            mListenerList.register(onNewBookArrived);
        }

        @Override
        public void unregisterOnNewBookArrived(IOnNewBookArrived onNewBookArrived) throws RemoteException {
            mListenerList.unregister(onNewBookArrived);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.fantasy.android.demo.android.permission.ACCESS_BOOK");
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }
}
