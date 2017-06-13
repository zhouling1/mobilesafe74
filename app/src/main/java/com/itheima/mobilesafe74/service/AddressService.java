package com.itheima.mobilesafe74.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe74.R;

public class AddressService extends Service {
    private TelephonyManager mTeleManger;
    private MyPhoneStateListener listener;
    private WindowManager mWManager;
    private WindowManager.LayoutParams mParams=new WindowManager.LayoutParams();
    private View mToastView;
    private TextView tv_toast;
    @Override
    public IBinder onBind(Intent intent) {
        //监听来电状态
        mTeleManger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listener = new MyPhoneStateListener();
        mTeleManger.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        mWManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        return null;
    }
    public void show(String phoneNumber){
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format= PixelFormat.TRANSLUCENT;
        params.type=WindowManager.LayoutParams.TYPE_PHONE;

        params.setTitle("Toast");
        params.gravity= Gravity.LEFT+Gravity.TOP;
        mToastView=View.inflate(this,R.layout.layout_mtoast,null);

        tv_toast= (TextView) mToastView.findViewById(R.id.tv_toast);

        //去添加一个挂在窗体的权限android.permission.SYSTEM_ALERT_WINDOW
        mWManager.addView(mToastView,params);

    }
    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                default:
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消电话状态的监听
        if(mTeleManger!=null&listener!=null){
            mTeleManger.listen(listener,PhoneStateListener.LISTEN_NONE);
        }
    }
}
