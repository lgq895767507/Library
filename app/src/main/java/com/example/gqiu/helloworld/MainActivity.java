package com.example.gqiu.helloworld;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private ExecutorService mThreadPool = Executors.newCachedThreadPool();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1
            );
        }

       /* List<SubscriptionInfo> list = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
        }
        SparseIntArray result = new SparseIntArray();
        if (list != null) {
            for (SubscriptionInfo item : list) {
                if (item == null) {
                    continue;
                }
                int subId = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    subId = item.getSubscriptionId();
                    int slotIndex = item.getSimSlotIndex();
                    result.put(subId, slotIndex);
                    int simCount = result.size();

                    Log.d("lgq", "isMultiSim: " + simCount);
                }
            }
        }
*/

  //      getPhoneType();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            sim1();
            sim2();
        }

        ViewConfiguration.get(this).getScaledTouchSlop();

        VelocityTracker.obtain().computeCurrentVelocity(100);

        //远程view 跨进程更新view
     /*   RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.activity_main);
        remoteViews.setTextViewText(id, "text");*/

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sim1() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        SubscriptionManager mSubscriptionManager = SubscriptionManager.from(this);
        SubscriptionInfo sub0 = mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(0);
        if (null != sub0) {
            //如果不为空 说明sim卡1存在
    /*
        sub0.getSubscriptionId() 获取sim卡1的 subId
    */
            Sim1SignalStrengthsListener mSim1SignalStrengthsListener = new Sim1SignalStrengthsListener(sub0.getSubscriptionId());
            //开始监听
            if (mTelephonyManager != null) {
                mTelephonyManager.listen(mSim1SignalStrengthsListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
        }

        boolean isSimCardExist = false;
        try {
            Method method = TelephonyManager.class.getMethod("getSimState", int.class);
            int simState = (Integer) method.invoke(mTelephonyManager, new Object[]{0});
            if (TelephonyManager.SIM_STATE_READY == simState) {
                isSimCardExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("lgq","isSimCardExist:" + isSimCardExist);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void sim2() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        SubscriptionManager mSubscriptionManager = SubscriptionManager.from(this);
        SubscriptionInfo sub0 = mSubscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(1);
        if (null != sub0) {
            //如果不为空 说明sim卡1存在
    /*
        sub0.getSubscriptionId() 获取sim卡1的 subId
    */
            Sim2SignalStrengthsListener mSim2SignalStrengthsListener = new Sim2SignalStrengthsListener(sub0.getSubscriptionId());
            //开始监听
            if (mTelephonyManager != null) {
                mTelephonyManager.listen(mSim2SignalStrengthsListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            }
        }

        boolean isSimCardExist = false;
        try {
            Method method = TelephonyManager.class.getMethod("getSimState", int.class);
            int simState = (Integer) method.invoke(mTelephonyManager, new Object[]{1});
            if (TelephonyManager.SIM_STATE_READY == simState) {
                isSimCardExist = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("lgq","sim2 isSimCardExist:" + isSimCardExist);
    }


    /**
     * 判断运营商类型
     */

    private static final int PHONE_TYPE_CMCC = 0;
    private static final int PHONE_TYPE_UNICOM = 1;
    private static final int PHONE_TYPE_CDMA = 2;

    private int getPhoneType() {
        int phoneType = -1;
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("phone count:" + tm.getPhoneCount());

        }

        String mccMnc = tm.getSimOperator();
        if ("46000".equals(mccMnc) || "46002".equals(mccMnc)
                || "46007".equals(mccMnc) || "46008".equals(mccMnc)
                || "45412".equals(mccMnc)) {
            phoneType = PHONE_TYPE_CMCC;
        } else if ("46001".equals(mccMnc) || "46006".equals(mccMnc)
                || "46009".equals(mccMnc)) {
            phoneType = PHONE_TYPE_UNICOM;
        } else if ("46003".equals(mccMnc) || "46005".equals(mccMnc)
                || "46011".equals(mccMnc) || "45502".equals(mccMnc)
                || "45507".equals(mccMnc)) {
            phoneType = PHONE_TYPE_CDMA;
        }
        return phoneType;
    }


    public static boolean sdMounted(Context context) {
        boolean isMounted = false;
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

        try {
            Method getVolumList = StorageManager.class.getMethod("getVolumeList", null);
            getVolumList.setAccessible(true);
            Object[] results = (Object[]) getVolumList.invoke(sm, null);
            if (results != null) {
                for (Object result : results) {
                    Method mRemoveable = result.getClass().getMethod("isRemovable", null);
                    Boolean isRemovable = (Boolean) mRemoveable.invoke(result, null);
                    if (isRemovable) {
                        Method getPath = result.getClass().getMethod("getPath", null);

                        String path = (String) mRemoveable.invoke(result, null);
                        Method getState = null;
                        if (sm != null) {
                            getState = sm.getClass().getMethod("getVolumeState", String.class);
                            String state = (String) getState.invoke(sm, path);
                            if (state.equals(Environment.MEDIA_MOUNTED)) {
                                isMounted = true;
                                break;
                            }
                        }


                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return isMounted;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //       int width = view.getMeasureWidth();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
      /*  view.post(new Runnable() {
            @Override
            public void run() {
                int width = view.getMeasureWidth();
            }
        });*/
    }

    public void sendOnclick(View view) {

       /* mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // socket.getInputStream()
                    DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                    writer.writeUTF("嘿嘿，你好啊，服务器.."); // 写一个UTF-8的信息

                    System.out.println("发送消息");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    public void connectClick(View view) {
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                try {
                    socket = new Socket("127.0.0.1", 9999);
                    System.out.println(socket.isConnected());
                    out = socket.getOutputStream();//获取输出流

                    out.write("abc".getBytes());//把相当于客服端数据写到server端
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(this, "connect", Toast.LENGTH_LONG).show();*/
    }
}
