package com.uniquedu.myeventbus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION = 0x23;
    @InjectView(R.id.textview_content)
    TextView textviewContent;
    @InjectView(R.id.button_start)
    Button buttonStart;
    @InjectView(R.id.button_sec)
    Button buttonSec;
    @InjectView(R.id.button_post_service)
    Button buttonPostService;
    private EventBus eventbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请Camera权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    STORAGE_PERMISSION);
        }
        eventbus = EventBus.getDefault();
        eventbus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage msg) {
        switch (msg.getType()) {
            case TYPE_ACTIVITY:
                //设置文本内容
                textviewContent.setText(msg.getContent());
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventbus.unregister(this);
    }


    @OnClick({R.id.button_start, R.id.button_sec, R.id.button_post_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (true) {
                            //此处发送消息
                            i++;
                            eventbus.post(new EventMessage("得到的消息" + i, EventMessage.Type.TYPE_ACTIVITY));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case R.id.button_sec:
                startService(new Intent(getApplicationContext(), MyIntentService.class));
                break;
            case R.id.button_post_service:
                eventbus.post(new EventMessage("UI界面发送给后台", EventMessage.Type.TYPE_SERVICE));
                break;
        }
    }


}
