package com.fantasy.android.demo.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Toast;

import com.fantasy.android.demo.R;
import com.fantasy.android.demo.android.baidu.face.FaceDetect;
import com.fantasy.android.demo.android.job.JobServiceActivity;
import com.fantasy.android.demo.android.socket.SocketService;
import com.fantasy.android.demo.android.socket.SocketTestActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by fantasy on 2018/3/10.
 */

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private ThreadPoolExecutor mMyExecutor;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors() + 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout_test);

        // 获得应用内存大小
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memeoryClass = activityManager.getMemoryClass();
        Log.d(TAG, "memeoryClass =" + memeoryClass);

        // 获得TouchSlop
        int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        Log.d(TAG, "touchSlop =" + touchSlop);

        getBinderPool();

        mMyExecutor = new ThreadPoolExecutor(CPU_COUNT,
                CPU_COUNT,
                10000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
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

    public void onCircleViewTest(View v) {
        Intent i = new Intent(this, CircleViewTestActivity.class);
        startActivity(i);
    }

    public void onNdkActivity(View v) {
        Intent i = new Intent(this, JniTestActivity.class);
        startActivity(i);
    }

    public void onJobServiceActivity(View v) {
        Intent i = new Intent(this, JobServiceActivity.class);
        startActivity(i);
    }

    public void onGassBLurActivity(View v) {
        //Intent i = new Intent(this, GassTestActivity.class);
        //startActivity(i);
    }


    private static class MyHandler extends Handler{

        private WeakReference<MainActivity> mMyActivity;
        public MyHandler(MainActivity activity) {
            mMyActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity = mMyActivity.get();
            if (activity != null) {
                activity.doSomeWork();
            }
        }
    };

    public void doSomeWork() {
        // do
    }
    public void onAsyncTaskTest(View v) {
        MyAsyncTask t1 = new MyAsyncTask();
        t1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"11");
        MyAsyncTask t2 = new MyAsyncTask();
        t2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"22");
        MyAsyncTask t3 = new MyAsyncTask();
        t3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"33");
        MyAsyncTask t4 = new MyAsyncTask();
        t4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"44");
        MyAsyncTask t5 = new MyAsyncTask();
        t5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"55");
        MyAsyncTask t6 = new MyAsyncTask();
        t6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"66");
        MyAsyncTask t7 = new MyAsyncTask();
        t7.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"77");
        MyAsyncTask t8 = new MyAsyncTask();
        t8.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"88");
        MyAsyncTask t9 = new MyAsyncTask();
        t9.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"99");
        MyAsyncTask t10 = new MyAsyncTask();
        t10.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1010");
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                Thread.sleep(1000);
                Log.d(TAG, "asyncTask doInBackground param=" + strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void onBaiduFaceDetect(View view) {

        mMyExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                FaceDetect.detect();
                Log.w(TAG, "face detect used=" + (System.currentTimeMillis() - current));
            }
        });

    }
}
