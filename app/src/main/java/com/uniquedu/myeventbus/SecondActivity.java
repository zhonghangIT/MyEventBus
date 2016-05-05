package com.uniquedu.myeventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SecondActivity extends AppCompatActivity {

    @InjectView(R.id.textview_receive)
    TextView textviewReceive;
    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage msg) {
        textviewReceive.setText(msg.getContent());
    }
}
