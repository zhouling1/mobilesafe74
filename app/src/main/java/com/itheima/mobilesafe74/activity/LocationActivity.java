package com.itheima.mobilesafe74.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

/**
 * Created by Administrator on 2017/6/14.
 */

public class LocationActivity extends Activity {
    private ImageView iv_Drg;
    private Button bt_Top;
    private Button bt_Bottom;
    private WindowManager mWManager;

    private int screenHeight;
    private int screenWidth;
    private long[] hints = new long[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_location);
        initView();
    }

    private void initView() {
        iv_Drg = (ImageView) findViewById(R.id.iv_drag);
        bt_Top = (Button) findViewById(R.id.bt_top);
        bt_Bottom = (Button) findViewById(R.id.bt_bottom);

        mWManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = mWManager.getDefaultDisplay().getWidth();
        screenHeight = mWManager.getDefaultDisplay().getHeight();
        System.out.println();

        int locationX = SpUtil.getInt(getApplication(), ConstantValue.LOCATIONX, 0);
        int locationY = SpUtil.getInt(getApplication(), ConstantValue.LOCATIONY, 0);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        System.out.println("locationX:" + locationX);

        params.leftMargin = locationX;
        params.topMargin = locationY;

        iv_Drg.setLayoutParams(params);

        if (locationY > screenHeight / 2) {
            System.out.println("隐藏");
            bt_Top.setVisibility(View.VISIBLE);
            bt_Bottom.setVisibility(View.INVISIBLE);
        } else {
            bt_Top.setVisibility(View.INVISIBLE);
            bt_Bottom.setVisibility(View.VISIBLE);
        }


        //双击
        iv_Drg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //(Object src, int srcPos, Object dest, int destPos, int length)

                System.arraycopy(hints, 1, hints, 0, hints.length - 1);
                hints[hints.length - 1] = System.currentTimeMillis();
                if ((hints[hints.length - 1] - hints[0]) < 500) {

                    int l = screenWidth / 2 - iv_Drg.getWidth() / 2;
                    int t = screenHeight / 2 - iv_Drg.getHeight() / 2;
                    int r = screenWidth / 2 + iv_Drg.getWidth() / 2;
                    int b = screenHeight / 2 + iv_Drg.getHeight() / 2;


                    iv_Drg.layout(l, t, r, b);
                    System.out.println("iv_Drg.getLeft():" + iv_Drg.getLeft());
                    SpUtil.putInt(LocationActivity.this, ConstantValue.LOCATIONX, iv_Drg.getLeft());
                    SpUtil.putInt(LocationActivity.this, ConstantValue.LOCATIONY, iv_Drg.getTop());
                }
            }
        });
        //拖动
        iv_Drg.setOnTouchListener(new View.OnTouchListener() {
            private int startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //初始位置
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //移动的距离
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        System.out.println("moveX:" + moveX + "moveY:" + moveY);

                        int disX = moveX - startX;
                        int disY = moveY - startY;

                        System.out.println("disX: " + disX + "    disY:" + disY);

                        int l = iv_Drg.getLeft() + disX;
                        int t = iv_Drg.getTop() + disY;
                        int r = iv_Drg.getRight() + disX;
                        int b = iv_Drg.getBottom() + disY;
                        System.out.println("触摸事件：  l:" + l + "  t:" + t + "   r:" + r + "   b:" + b);
                        if (l < 0) {
                            return true;
                        }
                        if (t < 0) {
                            return true;
                        }
                        if (r > screenWidth) {
                            return true;
                        }
                        if (b > screenHeight - 20) {
                            return true;
                        }

                        if (t > screenHeight / 2) {
                            bt_Top.setVisibility(View.VISIBLE);
                            bt_Bottom.setVisibility(View.GONE);
                        } else {
                            bt_Top.setVisibility(View.GONE);
                            bt_Bottom.setVisibility(View.VISIBLE);
                        }
                        iv_Drg.layout(l, t, r, b);

                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        SpUtil.putInt(LocationActivity.this, ConstantValue.LOCATIONX, iv_Drg.getLeft());
                        SpUtil.putInt(LocationActivity.this, ConstantValue.LOCATIONY, iv_Drg.getTop());
                        break;
                    default:
                        break;
                }
                //既要处理触摸，还要处理点击
                return false;
            }

        });
    }


}
