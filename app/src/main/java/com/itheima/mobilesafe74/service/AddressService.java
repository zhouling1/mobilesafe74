package com.itheima.mobilesafe74.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

import java.sql.SQLOutput;

public class AddressService extends Service {
    private TelephonyManager mTeleManger;
    private MyPhoneStateListener listener;
    private WindowManager mWManager;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mToastView;
    private TextView tv_toast;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //监听来电状态
        System.out.println("服务开启");
        mTeleManger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        listener = new MyPhoneStateListener();
        mTeleManger.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        System.out.println("监听");
        mWManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);


    }


    public void show(String phoneNumber) {
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                //   | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;

        params.setTitle("Toast");
        params.gravity = Gravity.LEFT + Gravity.TOP;
        mToastView = View.inflate(this, R.layout.layout_mtoast, null);

        tv_toast = (TextView) mToastView.findViewById(R.id.tv_toast);

        //去添加一个挂在窗体的权限android.permission.SYSTEM_ALERT_WINDOW

        //1.设置拖拽
        mToastView.setOnTouchListener(new View.OnTouchListener() {
            int startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        int disX = moveX - startX;
                        int disY = moveY - startY;


                        params.x = params.x + disX;
                        params.y = params.y + disY;

                        if (params.x < 0) {
                            params.x = 0;
                        }
                        if (params.y < 0) {
                            params.y = 0;
                        }

                        if (params.x + tv_toast.getWidth() > mWManager.getDefaultDisplay().getWidth()) {
                            params.x = mWManager.getDefaultDisplay().getWidth() - tv_toast.getWidth();
                        }
                        if (params.y + tv_toast.getHeight() > mWManager.getDefaultDisplay().getHeight() - 22) {
                            params.y = mWManager.getDefaultDisplay().getHeight() - 22 - tv_toast.getHeight();
                        }
                        mWManager.updateViewLayout(mToastView, params);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP:
                        SpUtil.putInt(AddressService.this, ConstantValue.LOCATIONX, mToastView.getLeft());
                        SpUtil.putInt(AddressService.this, ConstantValue.LOCATIONY, mToastView.getTop());
                        break;
                    default:
                        break;
                }


                return true;
            }
        });
        params.x = SpUtil.getInt(AddressService.this, ConstantValue.LOCATIONX, params.x);
        params.y = SpUtil.getInt(AddressService.this, ConstantValue.LOCATIONY, params.y);
        mWManager.addView(mToastView, params);
    }


    private class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            System.out.println("电话状态" + state);
            //响铃显示吐司，挂断之后移除
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mWManager != null && mToastView != null) {
                        mWManager.removeView(mToastView);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃");
                    show("jfla");
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
        if (mTeleManger != null & listener != null) {
            mTeleManger.listen(listener, PhoneStateListener.LISTEN_NONE);
        }
    }
}
