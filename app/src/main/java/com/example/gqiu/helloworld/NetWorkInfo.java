package com.example.gqiu.helloworld;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetWorkInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_info);
        Log.i("lgq", "wifi: " + isWifi(this));
        Log.i("lgq", "isMobile: " + isMobile(this));
    }

    private void checkNetWorkInfo() {
        ConnectivityManager mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        NetworkInfo info = null;
        if (mConnectivity != null) {
            info = mConnectivity.getActiveNetworkInfo();
        }

        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            Toast.makeText(this, "没有连接网络", Toast.LENGTH_SHORT).show();
            return;
        }
        int netType = info.getType();
        int netSubtype = info.getSubtype();

        if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
            Toast.makeText(this, "wifi", Toast.LENGTH_SHORT).show();
        } else if (netType == ConnectivityManager.TYPE_MOBILE && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {
            Toast.makeText(this, "mobile 网络", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没有连接网络", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = null;
        if (connectivityManager != null) {
            activeNetInfo = connectivityManager.getActiveNetworkInfo();
        }
        Log.i("lgq", "activeNetInfo:" + activeNetInfo.getType());
        ping();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private boolean isMobile(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = null;
        if (connectivityManager != null) {
            mMobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        //getState()方法是查询是否连接了数据网络
        return mMobileNetworkInfo != null && NetworkInfo.State.CONNECTED == mMobileNetworkInfo.getState();
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Toast.makeText(getApplicationContext(),"有网络",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"没有网络",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private MyHandler myHandler = new MyHandler();

    public void ping() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = null;
                try {
                    String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
                    Process p = Runtime.getRuntime().exec("ping -c 2 -w 3 " + ip);// -c:请求的次数  -w:等待回应的时间/秒
                    // 读取ping的内容，可以不加
                    InputStream input = p.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(input));
                    StringBuilder stringBuffer = new StringBuilder();
                    String content = "";
                    while ((content = in.readLine()) != null) {
                        stringBuffer.append(content);
                    }
                    Log.d("------ping-----", "result content : " + stringBuffer.toString());
                    // ping的状态
                    int status = p.waitFor();
                    if (status == 0) {
                        result = "success";
                        myHandler.sendEmptyMessage(1);
                    } else {
                        result = "failed";
                        myHandler.sendEmptyMessage(0);
                    }
                } catch (IOException e) {
                    result = "IOException";
                    myHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    result = "InterruptedException";
                    myHandler.sendEmptyMessage(0);
                } finally {
                    Log.d("----result---", "result = " + result);
                }
            }
        }).start();
    }
}
