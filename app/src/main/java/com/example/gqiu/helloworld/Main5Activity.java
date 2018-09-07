package com.example.gqiu.helloworld;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        TextView tv = findViewById(R.id.tv);

     //   new Thread(runnable).start();
   //     tv.setText(R.string.hello_world);
    //    ObjectAnimator.ofInt(tv,"width",500).setDuration(5000).start();



    }
}
