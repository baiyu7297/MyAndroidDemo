package com.fantasy.android.demo.android.socket;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fantasy.android.demo.R;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by fantasy on 2018/3/21.
 */

public class SocketTestActivity extends Activity {

    private TextView mText;
    private Button mSend;
    private EditText mEditText;

    Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_test_layout);

        mText = findViewById(R.id.textView);
        mSend = findViewById(R.id.clientSend);
        mEditText = findViewById(R.id.clientInput);

        HandlerThread mSendThread = new HandlerThread("send_thread");
        mSendThread.start();
        mHandler = new Handler(mSendThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Socket socket = new Socket("localhost", 1999);
                    OutputStream outputStream = socket.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write(mEditText.getText().toString());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void serverStart(View v) {
        Intent intent = new Intent(this, SocketService.class);
        startService(intent);
    }

    public void clientSend(View v) {
        mHandler.sendEmptyMessage(0);
    }
}
