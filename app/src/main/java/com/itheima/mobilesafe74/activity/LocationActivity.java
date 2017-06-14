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
    private int[] hints=new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_location);
        initView();
    }

    private void initView() {
        iv_Drg= (ImageView) findViewById(R.id.iv_drag);
        bt_Top= (Button) findViewById(R.id.bt_bottom);
        bt_Bottom= (Button) findViewById(R.id.bt_bottom);

        mWManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth =mWManager.getDefaultDisplay().getWidth();
        screenHeight=mWManager.getDefaultDisplay().getHeight();

        int locationX= SpUtil.getInt(getApplication(),ConstantValue.LOCATIONX,0);
        int locationY=SpUtil.getInt(getApplication(),ConstantValue.LOCATIONY,0);

        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.leftMargin=locationX;
        params.rightMargin=locationY;
        iv_Drg.setLayoutParams(params);

        if(locationY>screenHeight/2){
           bt_Top.setVisibility(View.INVISIBLE);
            bt_Bottom.setVisibility(View.VISIBLE);
        }else {
            bt_Top.setVisibility(View.VISIBLE);
            bt_Bottom.setVisibility(View.INVISIBLE);
        }
       iv_Drg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               System.arraycopy(hints,1,hints,0,hints.length-1);
               if((hints[hints.length-1]-hints[0])>500){
                   //双击
                   int l=screenWidth/2-iv_Drg.getWidth()/2;
                   int t=screenHeight/2-iv_Drg.getHeight()/2;
                   int r=screenWidth/2+iv_Drg.getWidth()/2;
                   int b=screenHeight/2+iv_Drg.getHeight()/2;

                   iv_Drg.layout(l,t,r,b);
               }
           }
       });
        iv_Drg.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
    }
}
