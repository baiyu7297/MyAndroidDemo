package com.fantasy.android.demo.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fantasy.android.demo.R;
import com.fantasy.android.demo.android.socket.SocketService;
import com.fantasy.android.demo.android.socket.SocketTestActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by fantasy on 2018/3/10.
 */

public class MainActivity extends Activity {
    
    private static final String TAG = "WANG_DEMO";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout_test);

        // 获得应用内存大小
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memeoryClass = activityManager.getMemoryClass();
        Log.d(TAG, "memeoryClass =" + memeoryClass);

        getBinderPool();
    }

    private BinderPool mBinderPool;
    private void getBinderPool() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(MainActivity.this);
            }
        }).start();
    }

    public void onParcelClick(View v) {
        final UserSerialize userinfo = new UserSerialize();
        //UserParcelable userinfo = new UserParcelable("wangyuan",31,"java");
        userinfo.age = 30;
        UserSerialize.name = "wang-->";
        userinfo.favorite = "android";
        final File file = new File(getCacheDir() + "/user1.txt");
        Observable observable = Observable.unsafeCreate(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

                try {

                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    oos.writeObject(userinfo);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Subscriber subscriber3 = new Subscriber() {
            @Override
            public void onCompleted() {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    UserSerialize user = (UserSerialize)ois.readObject();
                    Log.d(TAG, "user = " + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };

        observable.subscribe(subscriber3);
    }

    private void doRxJava() {
                /*Observable observable = Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("a");
                subscriber.onNext("b");
                subscriber.onNext("c");
                subscriber.onNext("d");
                subscriber.onCompleted();
            }
        });
        Observable
                .just("xx", "yy", "zz")
                .map(new Func1<String, Character>() {
                    @Override
                    public Character call(String s) {
                        if (!s.isEmpty()) {
                            return s.charAt(0);
                        }
                        return 'x';
                    }
                })
                .subscribe(new Action1<Character>() {
                    @Override
                    public void call(Character character) {
                        Log.d(TAG, "OUT CHAR=" + character);
                    }
                });

        Observable
                .just("aaa", "bbb", "ccc")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(new String[]{s, "=" + s+"="});
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "OUT str=" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });*/

        /*observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "Action1: s=" + s);
            }
        });*/
    }

    private Messenger mServerMessenger;
    public void onStartServiceClick(View v) {
        Intent service = new Intent(this, OtherProcessService.class);
        bindService(service, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mServerMessenger = new Messenger(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private Messenger mClientMessenger = new Messenger(new MyClientHandler(this));
    private static final int MESSAGE_RECEIVE_MSG = 10001;
    private static class MyClientHandler extends Handler {
        private Context mContext;
        public MyClientHandler(Context context) {
            mContext = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_MSG:
                    Bundle bundle = msg.getData();
                    Toast.makeText(mContext, bundle.getString("msg"),
                            Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onMessengerClick(View v) {
        if (mServerMessenger != null) {
            try {
                Message msg = Message.obtain();
                Bundle sendBundle = new Bundle();
                sendBundle.putString("msg", "Hello I'm client");
                msg.what = MESSAGE_RECEIVE_MSG;
                msg.setData(sendBundle);
                msg.replyTo = mClientMessenger;
                mServerMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Server is not running", Toast.LENGTH_SHORT).show();
        }
    }

    private IBookManager mBookManager;
    public void onAidlAddBook(View v) {
        if (mBookManager == null) {
            Intent intent = new Intent(this, BookManagerService.class);
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mBookManager = IBookManager.Stub.asInterface(service);
                    Toast.makeText(MainActivity.this,
                            "book service不存在,已创建", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, Context.BIND_AUTO_CREATE);
        } else {
            try {
                Book book = new Book("santi", 150);
                mBookManager.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onAidlGetBooks(View v) {
        if (mBookManager != null) {
            try {
                List<Book> bookList = mBookManager.getBookList();
                Log.d(TAG, "bookList = " + bookList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private IOnNewBookArrived mNewBookArrivedListener = new IOnNewBookArrived.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Toast.makeText(MainActivity.this,
                    "新书来了:" + book, Toast.LENGTH_SHORT).show();
        }
    };

    public void listenNewBook(View v) {
        if (mBookManager != null) {
            try {
                mBookManager.registerOnNewBookArrived(mNewBookArrivedListener);
                Toast.makeText(MainActivity.this, "开始监听", Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unListenNewBook(View v) {
        if (mBookManager != null && mNewBookArrivedListener != null) {
            try {
                mBookManager.unregisterOnNewBookArrived(mNewBookArrivedListener);
                Toast.makeText(MainActivity.this, "取消监听", Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void startSocket(View v) {
        Intent intent = new Intent(this, SocketTestActivity.class);
        startActivity(intent);
    }

    public void onBinderPoolRect(View v) {
        if (mBinderPool == null) return;
        try {
            IRect rect = IRectImpl.asInterface(mBinderPool.queryBinder(BinderPool.BINDER_CODE_RECT));
            int area = rect.area(20, 30);
            Toast.makeText(MainActivity.this, "width20height30, area=" + area, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onBinderPoolAdd(View v) {
        if (mBinderPool == null) return;
        try {
            ICalc calc = ICalcImpl.asInterface(mBinderPool.queryBinder(BinderPool.BINDER_CODE_CALC));
            int plus = calc.add(20, 30);
            Toast.makeText(MainActivity.this, "20+30=" + plus, Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
