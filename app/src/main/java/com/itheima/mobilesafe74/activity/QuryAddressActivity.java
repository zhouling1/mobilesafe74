package com.itheima.mobilesafe74.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.engin.AdressDao;

/**
 * Created by zhouling on 2017/6/10.
 */

public class QuryAddressActivity extends Activity{
    private EditText ed_Phone;
    private Button btn_query;
    private String location;
    private TextView tv_query_result;
    private Handler mhandler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_query_result.setText(location);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);
        initView();
    }

    private void initView() {
        ed_Phone= (EditText) findViewById(R.id.et_phone);
        btn_query= (Button) findViewById(R.id.bt_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(ed_Phone.getText())){
                    query(ed_Phone.getText()+"");
                }else {
                    Animation shake= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_layout);

                    ed_Phone.startAnimation(shake);
                    Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    //vibrator.vibrate(1000);
                    vibrator.vibrate(new long[]{2000,5000,3000},3);
                }
            }
        });

    }

    private void query(final String phone){
      new Thread(){
          @Override
          public void run() {

              location = AdressDao.getAddress(phone);
              mhandler.sendEmptyMessage(0);
          }
      }.start();

    }


}
