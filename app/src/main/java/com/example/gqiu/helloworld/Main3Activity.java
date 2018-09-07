package com.example.gqiu.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class Main3Activity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(Main3Activity.this, Main2Activity.class));
            }
        });

        linearLayout = findViewById(R.id.linearLayout);
        IntentFilter filter = new IntentFilter(Main2Activity.MY_REMOTE_ACTION);
        registerReceiver(mRemoteBroadcastReceiver,filter);
    }

    private BroadcastReceiver mRemoteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("lgq","broadcast receiver:");
            RemoteViews remoteViews = intent.getParcelableExtra(Main2Activity.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null){
                updateUi(remoteViews);
            }
        }
    };

    private void updateUi(RemoteViews remoteViews) {
        View view = remoteViews.apply(this, linearLayout);
        linearLayout.addView(view);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRemoteBroadcastReceiver);
        super.onDestroy();
    }
}
