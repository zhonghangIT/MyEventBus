package com.uniquedu.myeventbus;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyIntentService extends IntentService {
    EventBus eventBus;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    public MyIntentService() {
        this("测试线程");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        int j = 0;
        while (true) {
            j++;
            eventBus.post(new EventMessage("后台发送的消息" + j, EventMessage.Type.TYPE_ACTIVITY));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage msg) {
        if (msg.getType() == EventMessage.Type.TYPE_SERVICE) {
            Toast.makeText(MyIntentService.this, "后台接收到消息" + msg.getContent(), Toast.LENGTH_SHORT).show();
        }
    }
}
