package com.example.gqiu.helloworld;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by gq.liu on 18-8-28.
 */
public class Sim2SignalStrengthsListener extends PhoneStateListener {

    public Sim2SignalStrengthsListener(int subId) {
        super();
        ReflectUtil.setFieldValue(this, "mSubId", subId);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        int level = getSignalStrengthsLevel(signalStrength);
        Log.i("lgq","sim 2 level:" + level);
    }

    private int getSignalStrengthsLevel(SignalStrength signalStrength) {
        int level = -1;
        try {
            Method levelMethod = SignalStrength.class.getDeclaredMethod("getLevel");
            level = (int) levelMethod.invoke(signalStrength);
        } catch (Exception e) {
            Log.e("lgq", e.getMessage());
        }
        return level;
    }

}
