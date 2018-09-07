package com.example.gqiu.helloworld;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RemoteViews;

public class Main2Activity extends AppCompatActivity {

    public static final String EXTRA_REMOTE_VIEWS  = "remote_views";
    public static final String MY_REMOTE_ACTION = "remote_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RemoteViews的布局
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_remote_notification);
        //设置RemoteViews显示的内容
        remoteViews.setTextViewText(R.id.msgTextView, "msg from progress:" + Process.myPid());
        //设置RemoteViews的点击事件---这里点击后再次返回到当前activity
        final PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), Main2Activity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.msgTextView, openActivity2PendingIntent);

        //发送广播的方式通知另外线程更新ui
        Intent intent = new Intent();
        intent.setAction(MY_REMOTE_ACTION);
        intent.putExtra(EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }

}
